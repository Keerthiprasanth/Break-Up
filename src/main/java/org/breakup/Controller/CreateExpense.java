package org.breakup.Controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.breakup.Service.CreateExpenseService;

import java.util.*;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class CreateExpense {

    private MongoDatabase mongoDatabase;
    private Scanner sn;
    Expense expense;
    private CreateExpenseService service;
    public String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    public CreateExpense(MongoDatabase mongoDatabase) {
        System.out.println("Please enter the below details to create an expense");
        this.expense = new Expense();
        this.service = new CreateExpenseService();
        this.mongoDatabase = mongoDatabase;
        this.sn = new Scanner(System.in);
    }
    public void fetchAndUpdateDatabase() {
        if (!mongoDatabase.listCollectionNames()
                .into(new ArrayList()).contains("expense")) {
            mongoDatabase.createCollection("expense");
        }
        MongoCollection<Expense> expenseCollection = mongoDatabase.getCollection("expense", Expense.class);
        long expenseId = expenseCollection.countDocuments() + 1;
        MongoCollection<Member> memberCollection = mongoDatabase.getCollection("member", Member.class);
        takeInputFromUser(memberCollection);
        expense.setExpenseId(expenseId);
        expenseCollection.insertOne(expense);
        updateMembersDues(memberCollection);
        System.out.println("Expense is created Successfully with ID:"+expense.getExpenseId());
    }

    public void takeInputFromUser(MongoCollection<Member> memberCollection) {
        int totalMembers = inputTotalMembers();
        System.out.println("Input the email id of all the members");
        inputEmailIds(totalMembers, memberCollection);
        System.out.println("Input the description of the expense");
        expense.setDescription(sn.next());
        expense.setTotalAmount(inputTotalAmount());
        System.out.println("Input the email id of the member who paid the expense");
        expense.setPaidBy(checkPaidByExistence(memberCollection).getEmailId());
        System.out.println("How do you want to split the expenses?");
        expense.setSplitType(splitTypeValidation());
        setExpense();
    }
    private int inputTotalMembers() {
        System.out.println("Input the total number of members including you to split the expense (HINT: Number must be >1)");
        try {
            sn = new Scanner(System.in);
            int totalMembers = sn.nextInt();
            if (service.validateTotalMembers(totalMembers)) {
                return totalMembers;
            } else {
                System.out.println("Please enter a valid number");
                return inputTotalMembers();
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            return inputTotalMembers();
        }
    }

    private void inputEmailIds(int totalMembers, MongoCollection<Member> memberCollection) {
        List<MemberExpense> membersForExpenseList = new ArrayList<>();
        for (int i=0; i<totalMembers; i++) {
            System.out.print("EmailId of Member " + (i + 1) + ": ");
            MemberExpense memberExpense = new MemberExpense();
            memberExpense.setMember(emailIdValidation(memberCollection));
            membersForExpenseList.add(memberExpense);
        }
        expense.setMemberExpenseList(membersForExpenseList);
    }
    private Member emailIdValidation(MongoCollection<Member> memberMongoCollection) {
        String emailId = sn.next();
        if (service.validateEmailPattern(emailId)) {
            Member member = memberMongoCollection.find(eq("emailId", emailId)).first();
            if(member != null) {
                return member;
            }
            System.out.println("Please enter the email id of registered member");
        } else {
            System.out.println("Please enter a valid email id");
        }
        return emailIdValidation(memberMongoCollection);
    }
    private Double inputTotalAmount() {
        System.out.println("Input the total amount to be split");
        try {
            return sn.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            return inputTotalAmount();
        }
    }

    private Member checkPaidByExistence(MongoCollection<Member> memberMongoCollection) {
        Member paidBy = emailIdValidation(memberMongoCollection);
        if (service.checkForPayerId(expense.getMemberExpenseList(), paidBy.getEmailId()) > 0) {
            return paidBy;
        } else {
            System.out.println("Please enter email ID of MEMBER of this Expense");
            return checkPaidByExistence(memberMongoCollection);
        }
    }
    private int splitTypeValidation() {
        System.out.println("Input 1 to split equally; Input 2 to split by shares; Input 3 to split by amount");
        try {
            int splitType = sn.nextInt();
            if (service.checkForSplitType(splitType)) {
                System.out.println("Please enter a valid input");
                return splitTypeValidation();
            } else {
                return splitType;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid input");
            return splitTypeValidation();
        }
    }

    private void setExpense() {
        service.setPaymentFlag(expense);
        for (int i=0; i< expense.getMemberExpenseList().size(); i++) {
            if (expense.getSplitType() == 1) {
                service.setExpenseEqually(expense);
            } else if (expense.getSplitType() == 2) {
                System.out.println("Enter the shares of all members");
                expense.getMemberExpenseList().get(i).setShare(validateMemberShare(i));
            } else if (expense.getSplitType() == 3) {
                System.out.println("Enter the expense amount for all members");
                expense.getMemberExpenseList().get(i).setExpense(validateExpenseAmount(i));
            }
        }
        if (expense.getSplitType() == 2) {
            service.calculateByShare(expense);
        }
        if (service.validateTotalExpenseByMemberExpense(expense)) {
            System.out.println("Please enter the member share/expense to sum up to " + expense.getTotalAmount());
            setExpense();
        }
    }
    private int validateMemberShare(int i) {
        System.out.print("Share of Member " + (i+1) + ": ");
        try {
            int share = sn.nextInt();
            if (service.validateShareValue(share)) {
                return share;
            } else {
                System.out.println("Please enter a valid number");
                return validateMemberShare(i);
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            return validateMemberShare(i);
        }
    }

    private Double validateExpenseAmount(int i) {
        System.out.print("Expense of Member " + (i+1) + ": ");
        try {
            Double expenseAmount = sn.nextDouble();
            if (service.validateExpenseValue(expenseAmount)) {
                System.out.println("Please enter a valid amount");
                return validateExpenseAmount(i);
            } else {
                return expenseAmount;
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid amount");
            return validateExpenseAmount(i);
        }
    }

    private void updateMembersDues(MongoCollection memberCollection) {
        for (MemberExpense memberExpense: expense.getMemberExpenseList()) {
            Member member = (Member) memberCollection.find(eq("emailId", memberExpense.getMember().getEmailId())).first();
            service.updateMemberValues(member, expense, memberExpense);
            memberCollection.updateOne(eq("emailId", member.getEmailId()), combine(set("totalBalance", member.getTotalBalance()), set("totalOwed", member.getTotalOwed()), set("totalPayable", member.getTotalPayable())));
        }
    }
    }

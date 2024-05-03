package org.breakup.Controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.breakup.Service.ViewExpenseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewExpense {
    private MongoDatabase mongoDatabase;
    private String loggedInMember;
    public ViewExpense(MongoDatabase _mongoDatabase) {
        this.mongoDatabase = _mongoDatabase;
    }


    public void viewLoggedInDues(Member member) {
        loggedInMember = member.getEmailId();
        System.out.println("Member Name: " + member.getMemberName());
        getExpensesWithMembers();
    }

    public void getExpensesWithMembers() {
        MongoCollection<Expense> expenseCollection = mongoDatabase.getCollection("expense", Expense.class);
        var iterator = expenseCollection.find().iterator();
        List<Expense> expenseList = new ArrayList<>();
        while (iterator.hasNext()){
            expenseList.add(iterator.next());
        }
        ViewExpenseService v = new ViewExpenseService();
        ViewExpenseService.ExpenseList el= v.getExpensesWithMembers(expenseList);
        List<Expense> owedList = el.getOwedList();
        List<Expense> payableList = el.getPayableList();
        for (Expense expense: expenseList) {
            for (MemberExpense memberExpense: expense.getMemberExpenseList()) {
                if (memberExpense.getMember().getEmailId().equals(loggedInMember) && memberExpense.getPaymentFlag() != null) {
                    if (memberExpense.getPaymentFlag().equals("+")) {
                        owedList.add(expense);
                    } else {
                        payableList.add(expense);
                    }
                }
            }
        }
        System.out.println("Expenses to be paid");
        for (Expense expense: payableList) {
            System.out.println("Expense description: " + expense.getDescription() + " and the amount is " +  expense.getTotalAmount() + " paid by " + expense.getPaidBy() + " with expense Id " + expense.getExpenseId());
        }

        System.out.println("Expense owed");
        for (Expense expense: owedList) {
            System.out.println("Owed description: " + expense.getDescription() + " and the amount is " +  expense.getTotalAmount() + " paid by " + expense.getPaidBy() + " with expense Id " + expense.getExpenseId());
        }



    }

/*
    public void showMoreOptions() {
        System.out.println(" ");
        System.out.println("Select an option to continue further:");
        int option;

        do {
            System.out.println(" ");
            System.out.println("1. Create Expense");
            System.out.println("2. Edit Expense");
            System.out.println("3. Delete Expense");
            System.out.println("4. SettleUp Expense");
            System.out.println("5. View Expense");
            System.out.println("6. LogOut");
            System.out.println(" ");
            System.out.println("Enter your choice");
            System.out.println(" ");
            Scanner sn = new Scanner(System.in);
            option = sn.nextInt();
            System.out.println("\n");

            switch (option) {
                case 1:
                    CreateExpense e = new CreateExpense(mongoDatabase);
                    e.fetchAndUpdateDatabase();
                    break;

                case 2:
                    EditExpense x = new EditExpense(mongoDatabase);

                    break;

                case 3:
                    DeleteExpense d = new DeleteExpense(mongoDatabase);
                    break;
                case 4:
          //          SettleExpense s = new SettleExpense(mongoDatabase);
                    break;
                case 5:
                    ViewExpense v = new ViewExpense(mongoDatabase);
                    break;
                case 6:
                    System.out.println("");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option! Please enter again");
                    break;
            }
        } while (option != 5);

    }
*/
}

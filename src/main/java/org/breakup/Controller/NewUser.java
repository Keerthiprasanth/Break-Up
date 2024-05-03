package org.breakup.Controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.breakup.Model.Member;
import org.breakup.Service.NewUserService;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class NewUser {
    private final MongoDatabase mongoDatabase;
    public Member member;
    private final NewUserService service;
    public NewUser(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
        this.service = new NewUserService();
    }

    public void user() {
        Member member = new Member();
        List<Member> memberList = new ArrayList<>();
        System.out.println("User Registration");
        validateAndSetName(memberList,member);
        validateAndSetPhoneNo(memberList,member);
        validateAndSetEmailId(memberList,member);
        validateAndSetPassword(memberList,member);
        if (!mongoDatabase.listCollectionNames().into(new ArrayList()).contains("member")) {
            mongoDatabase.createCollection("member");
        }
        MongoCollection<Member> collection = mongoDatabase.getCollection("member", Member.class);
        collection.insertOne(member);
        System.out.println("User registered successfully");
        login();
    }

    public void login(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the following details to login!");
        System.out.print("Enter your EmailID : ");
        String mailLogin = input.nextLine();
        System.out.print("Enter your password : ");
        String password = input.nextLine();
        Bson filter = and(eq("emailId", mailLogin.toLowerCase()));
        MongoCollection<Member> coll = mongoDatabase.getCollection("member", Member.class);
        var collum = coll.find(filter);
        if(collum.iterator().available() < 1){
            System.out.println("You have not entered a valid email id");
            login();
        }
        member = collum.first();
        if(service.validateMemberPassword(password,member)){
            System.out.println("You have not entered a valid password");
            login();
        }
        System.out.println("Hi "+member.getMemberName());
        showMoreOptions();
    }

    private void validateAndSetName(List<Member> memberList,Member member) {
        System.out.print("Enter your Name : ");
        Scanner sn = new Scanner(System.in);
        String name = sn.next();
        if(service.nameValidation(name)) {
            member.setMemberName(name);
            memberList.add(member);
        } else {
            System.out.println("Please enter a valid user name");
            validateAndSetName(memberList,member);
        }
    }

    private void validateAndSetEmailId(List<Member> memberList,Member member) {
        System.out.print("Enter your Email ID : ");
        Scanner sn = new Scanner(System.in);
        String emailId = sn.next();
        if(service.emailIdValidation(emailId)) {
            member.setEmailId(emailId);
            memberList.add(member);
        } else {
            System.out.println("Please enter a valid Email ID");
            validateAndSetEmailId(memberList,member);
        }
    }

    private void validateAndSetPhoneNo(List<Member> memberList,Member member) {
        System.out.print("Enter your phone number : ");
        Scanner sn = new Scanner(System.in);
        String phoneNo = sn.next();
        if(service.phoneNoValidation(phoneNo)) {
            member.setPhoneNo(phoneNo);
            memberList.add(member);
        } else {
            System.out.println("Please enter a valid phone number");
            validateAndSetPhoneNo(memberList,member);
        }
    }

    private void validateAndSetPassword(List<Member> memberList,Member member){
        System.out.print("Create a new Password : ");
        Scanner sn = new Scanner(System.in);
        String password = sn.next();
        if (service.passwordValidation(password)) {
            member.setPassword(password);
            memberList.add(member);
        }
        else{
            System.out.println("Error : Password length should be minimum 8 and includes an uppercase,lowercase,number and special_character");
            validateAndSetPassword(memberList,member);
        }
    }

    private void showMoreOptions() {
        try {
            System.out.println(" ");
            System.out.print("Select an option to continue further : \n");
            int option;
            do {
                System.out.println();
                System.out.println("1. Create Expense");
                System.out.println("2. View Expense");
                System.out.println("3. Edit Expense");
                System.out.println("4. Delete Expense");
                System.out.println("5. SettleUp Expense");
                System.out.println("6. Logout");
                System.out.println();
                System.out.print("Enter : ");
                Scanner sn = new Scanner(System.in);
                option = sn.nextInt();
                System.out.println();
                switch (option) {
                    case 1 -> {
                        CreateExpense c = new CreateExpense(mongoDatabase);
                        c.fetchAndUpdateDatabase();
                    }
                    case 2 -> {
                        ViewExpense v = new ViewExpense(mongoDatabase);
                        v.viewLoggedInDues(member);
                    }
                    case 3 -> {
                        new EditExpense(mongoDatabase, member);
                    }
                    case 4 -> {
                       new DeleteExpense(mongoDatabase);
                    }
                    case 5 -> {
                        new SettleExpense(mongoDatabase, member);
                    }
                    case 6 -> {
                        System.out.println();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Option! Please enter again");
                }
            } while (true);
        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            showMoreOptions();
        }
    }
}
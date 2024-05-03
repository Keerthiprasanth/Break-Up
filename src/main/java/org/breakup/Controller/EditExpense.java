package org.breakup.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.set;

import org.breakup.Model.Expense;

import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.management.Query;

public class EditExpense {
    // The Edit Page

    public EditExpense(MongoDatabase mongoDatabase, Member member) {
        Expense expense = new Expense();

        System.out.println("Welcome to Edit Page: ");
        System.out.println("Please Enter your ExpenseID: ");
        Scanner scan = new Scanner(System.in);
        int expenseId = Integer.parseInt(scan.next());
        BasicDBObject query = new BasicDBObject();
        query.put("expenseId",expenseId);

        MongoCollection<Document> collection = mongoDatabase.getCollection("expense");
        query.putAll((BSONObject) query);
        FindIterable<Expense> userTbl = mongoDatabase.getCollection("expense", Expense.class).find(query);

        for (Expense dbData : userTbl) {
            System.out.println(dbData.getTotalAmount());
            expense = dbData;
            System.out.println(expense.getExpenseId());
        }



        System.out.println("1.Edit Description 2.Edit Amount 3.Edit both Description and Amount");
        System.out.println("Please Enter your Choice: ");
        String choice = scan.next();
        String description;
        Double amount;
        switch (choice) {
            case "1":
                System.out.println("Description");
                System.out.println("Please Enter your Description to Change: ");
                description = scan.next();
                expense = get(expense, description, mongoDatabase);
                System.out.println(expense.getDescription());
                updateExpenseDetailsInDB(mongoDatabase,expense);
                break;
            case "2":
                System.out.println("Amount");
                System.out.println("Please Enter your New Amount: ");
                amount = scan.nextDouble();
                expense = get(expense, amount, mongoDatabase);
                for (MemberExpense mem : expense.getMemberExpenseList()) {
                    System.out.println(mem.getExpense());
                }
                updateExpenseDetailsInDB(mongoDatabase,expense);
                break;
            case "3":
                System.out.println("Description and Amount");
                System.out.println("Please Enter your Description to Change: ");
                description = scan.next();
                System.out.println("Please Enter your New Amount: ");
                amount = scan.nextDouble();
                expense = get(expense, description, mongoDatabase);
                expense = get(expense, amount, mongoDatabase);
                System.out.println(expense.getDescription());
                for (MemberExpense mem : expense.getMemberExpenseList()) {
                    System.out.println(mem.getExpense());
                }
                updateExpenseDetailsInDB(mongoDatabase,expense);
                break;
        }

    }

    private void updateExpenseDetailsInDB(MongoDatabase mongoDatabase,Expense expense) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("expense");
        System.out.println(new Gson().toJson(expense));
        Document doc = Document.parse(new Gson().toJson(expense));
        collection.updateOne(new Document("expenseId", expense.getExpenseId()),
                new Document("$set",doc));
    }

    public Expense get(Expense expense, String description, Double amount, MongoDatabase mongoDatabase) {

        expense.setDescription(description);
        return expense;
    }

    public Expense get(Expense expense, String description, MongoDatabase mongoDatabase) {
        expense.setDescription(description);

        return expense;
    }

    public Expense get(Expense expense, Double amount, MongoDatabase mongoDatabase) {
        expense.setTotalAmount(amount);
        Double newindividualshare = calculateBySplitEqually(amount, expense.getMemberExpenseList().size());
        System.out.println("newindividualshare" + newindividualshare);
        for (MemberExpense mem : expense.getMemberExpenseList()) {
            mem.setExpense(newindividualshare);
        }
        return expense;
    }

    public Double calculateBySplitEqually(Double totalAmount, int memberList) {
        return totalAmount / memberList;
    }
}

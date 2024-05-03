package org.breakup.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.breakup.Service.CreateExpenseService;
import org.breakup.Service.DeleteExpenseService;
import org.bson.Document;
import org.bson.conversions.Bson;

import org.breakup.Model.Expense;

public class DeleteExpense {
    private DeleteExpenseService service;


    public DeleteExpense(MongoDatabase mongoDatabase) {
        MongoCollection<Document> memcol = mongoDatabase.getCollection("expense");
        List<Document> mem = memcol.find()
                .into(new ArrayList<>());
        for (Document member : mem) {
            System.out.print(member.toJson());
        }

        System.out.println("\n enter the transaction ID which is to be deleted: ");
        Scanner sn = new Scanner(System.in);
        int transactionID = sn.nextInt();
        memcol.deleteOne(eq("expenseId", transactionID));
        System.out.println("\n Deleted the transaction ID: " + transactionID);
        System.out.println("\n");
        Document amount = (Document)memcol.find(Filters.eq("expenseId", transactionID)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"ExpenseAmount"})})).first();
        //totalBalance(mongoDatabase,user2,user1,amount);
    }
    public static void totalBalance(MongoDatabase mongoDatabase, String paiduser, String oweduser, String amount)
    {

        MongoCollection<Document> memcol = mongoDatabase.getCollection("member");
        Document paiduser_payable = (Document)memcol.find(Filters.eq("emailId", paiduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_owed = (Document)memcol.find(Filters.eq("emailId", oweduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        double paiduser_totalpayable = paiduser_payable.getDouble("totalPayable");
        double oweduser_totalowed = oweduser_owed.getDouble("totalOwed");
        double paiduser_balance = DeleteExpenseService.balance(oweduser_totalowed , paiduser_totalpayable);
        double oweduser_balance = DeleteExpenseService.updateValues(paiduser_balance , paiduser_totalpayable);
        Bson filter = Filters.eq("emailId", paiduser);
        memcol.findOneAndUpdate(filter, Updates.set("totalBalance", paiduser_balance));
        Bson filter1 = Filters.eq("emailId", oweduser);
        memcol.findOneAndUpdate(filter1, Updates.set("totalBalance", oweduser_balance));
    }

}

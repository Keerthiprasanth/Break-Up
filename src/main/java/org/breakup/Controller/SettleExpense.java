//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.breakup.Controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import java.util.Scanner;

import org.breakup.Model.Member;
import org.breakup.Service.SettleExpenseService;
import org.bson.Document;
import org.bson.conversions.Bson;

public class SettleExpense {
    public SettleExpense(MongoDatabase mongoDatabase, Member member) {

        String user1 = member.getEmailId();
        System.out.println("Enter EmailID of payee:");
        Scanner ID2 = new Scanner(System.in);
        String user2 = ID2.nextLine();
        MongoCollection<Document> memcol = mongoDatabase.getCollection("member");
        Document paiduser_owed = (Document)memcol.find(Filters.eq("emailId", user2)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
while(paiduser_owed.getDouble("totalOwed")<=0d)
{
    System.out.println("You Don't have any pending settlements with Payee:"+user2);
    System.out.println("Enter EmailID of payee:");
    ID2 = new Scanner(System.in);
    user2 = ID2.nextLine();
}

        System.out.println("Enter amount to be paid:");
        Scanner pay = new Scanner(System.in);
        Double amount = pay.nextDouble();
      //  Document paiduser_owed1 = (Document)memcol.find(Filters.eq("emailId", user2)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        while(paiduser_owed.getDouble("totalOwed")<amount) {
            System.out.println("You are paying more than your due to payee:");
            System.out.println("Enter amount to be paid:");
            pay = new Scanner(System.in);
            amount = pay.nextDouble();
        }
        //MongoCollection<Document> memcol1 = mongoDatabase.getCollection("member");
        updateExpense(mongoDatabase, user2, user1, amount);
        totalBalance(mongoDatabase, user1, user2);
        MongoCollection<Document> memcol1 = mongoDatabase.getCollection("member");
        Document oweduser_payable = (Document)memcol1.find(Filters.eq("emailId", user1)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_owed = (Document)memcol1.find(Filters.eq("emailId", user1)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        Document oweduser_Balance = (Document)memcol1.find(Filters.eq("emailId", user1)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalBalance"})})).first();

        System.out.println("your totalOwed:"+oweduser_owed.getDouble("totalOwed"));
        System.out.println("your totalPayable:"+oweduser_payable.getDouble("totalPayable"));
        System.out.println("your totalBalance:"+oweduser_Balance.getDouble("totalBalance"));
    }

    public static void updateExpense(MongoDatabase mongoDatabase, String paiduser, String oweduser, Double payment) {
        MongoCollection<Document> memcol = mongoDatabase.getCollection("member");
        Document paiduser_owed = (Document)memcol.find(Filters.eq("emailId", paiduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        Document paiduser_payable = (Document)memcol.find(Filters.eq("emailId", paiduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_payable = (Document)memcol.find(Filters.eq("emailId", oweduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_owed = (Document)memcol.find(Filters.eq("emailId", oweduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        double paiduser_exp = SettleExpenseService.settleup(paiduser_owed.getDouble("totalOwed"), payment);
        Bson filter = Filters.eq("emailId", paiduser);
        memcol.findOneAndUpdate(filter, Updates.set("totalOwed", paiduser_exp));
        double oweduser_exp = SettleExpenseService.settleup(oweduser_payable.getDouble("totalPayable"), payment);
        Bson filter1 = Filters.eq("emailId", oweduser);
        memcol.findOneAndUpdate(filter1, Updates.set("totalPayable", oweduser_exp));
    }

    public static void totalBalance(MongoDatabase mongoDatabase, String paiduser, String oweduser) {
        MongoCollection<Document> memcol = mongoDatabase.getCollection("member");
        Document paiduser_owed = (Document)memcol.find(Filters.eq("emailId", paiduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        Document paiduser_payable = (Document)memcol.find(Filters.eq("emailId", paiduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_payable = (Document)memcol.find(Filters.eq("emailId", oweduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalPayable"})})).first();
        Document oweduser_owed = (Document)memcol.find(Filters.eq("emailId", oweduser)).projection(Projections.fields(new Bson[]{Projections.excludeId(), Projections.include(new String[]{"totalOwed"})})).first();
        double paiduser_totalowed = paiduser_owed.getDouble("totalOwed");
        double paiduser_totalpayable = paiduser_payable.getDouble("totalPayable");
        double oweduser_totalowed = oweduser_owed.getDouble("totalOwed");
        double oweduser_totalpayable = oweduser_payable.getDouble("totalPayable");
        double paiduser_balance = SettleExpenseService.balance(paiduser_totalowed, paiduser_totalpayable);
        double oweduser_balance = SettleExpenseService.balance(oweduser_totalowed, oweduser_totalpayable);
        Bson filter = Filters.eq("emailId", paiduser);
        memcol.findOneAndUpdate(filter, Updates.set("totalBalance", paiduser_balance));
        Bson filter1 = Filters.eq("emailId", oweduser);
        memcol.findOneAndUpdate(filter1, Updates.set("totalBalance", oweduser_balance));
    }
}

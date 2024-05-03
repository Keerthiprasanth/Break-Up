package org.breakup;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.breakup.Controller.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class Main {
    public static void main(String[] args) {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = MongoClients.create("mongodb+srv://breakup:breakup@cluster0.p3rjm4h.mongodb.net/splitdb?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("splitdb");
        database = database.withCodecRegistry(pojoCodecRegistry);
        System.out.println("Welcome to Break Up!");
        Main main = new Main();
        main.inputOperation(database);
    }
    public void inputOperation(MongoDatabase mongoDatabase) {
        try
        {
        System.out.println("Enter 1 to SignUp (or) Enter 2 to Login ");
        Scanner sn = new Scanner(System.in);
        int operation = sn.nextInt();
        NewUser newUser = new NewUser(mongoDatabase);
        switch (operation) {
            case 1 -> newUser.user();
            case 2 -> newUser.login();
            default -> {
                System.out.println("Invalid Option! Please enter again");
                inputOperation(mongoDatabase);
            }
        }
        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a valid number");
            inputOperation(mongoDatabase);
        }
    }
}
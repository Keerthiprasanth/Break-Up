package org.breakup.Service;


import org.breakup.Model.Expense;

public class EditExpenseService {
    //This is update the Description

    public Expense updateDescription(Expense expense, String description) {
        if (expense!=null){
            expense.setDescription(description);
            return  expense;
        }
        else
            return null;
    }
    public Expense updateAmount(Expense expense, Double amount) {
        if (expense!=null) {
            expense.setTotalAmount(amount);
            return expense;
        }
        else
            return null;
//        calculateTotalAmount(expense);
//        for (MemberExpense mem : expense.getMemberExpenseList()) {
//            System.out.println(mem.getExpense());
//        }
    }

    public Expense updateDescriptionAndAmount(Expense expense, String description, double amount) {
        expense.setDescription(description);
        expense.setTotalAmount(amount);
//        calculateTotalAmount(expense);
//        for (MemberExpense mem : expense.getMemberExpenseList()) {
//            System.out.println(mem.getExpense());
//        }
        return expense;
    }

    public Double calculateBySplitEqually(Double totalAmount, int memberList) {
        return totalAmount / memberList;
    }

}
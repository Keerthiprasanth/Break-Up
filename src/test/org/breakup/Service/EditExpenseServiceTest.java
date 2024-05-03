package org.breakup.Service;

import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseServiceTest {
    //Test
    @org.junit.jupiter.api.Test
    void updateDescription() {
        Expense e1 = new Expense();
        e1.setDescription("tv");
        List<Expense> ExpenseList = new ArrayList();
        ExpenseList.add(e1);
        e1.setDescription("Plasma");
        Expense output = new EditExpenseService().updateDescription(e1, e1.getDescription());
        Assert.assertTrue(e1.getDescription()==output.getDescription());
    }

    @org.junit.jupiter.api.Test
    void updateAmount() {
        Expense e2 = new Expense();
        e2.setTotalAmount(Double.valueOf("200"));
        List<Expense> ExpenseList = new ArrayList();
        ExpenseList.add(e2);
        e2.setTotalAmount(Double.valueOf("300"));
        Expense output = new EditExpenseService().updateAmount(e2, e2.getTotalAmount());
        Assert.assertTrue(e2.getTotalAmount()==output.getTotalAmount());
    }

    @org.junit.jupiter.api.Test
    void updateDescriptionAndAmount() {
        Expense e3 = new Expense();
        e3.setDescription("mobile");
        e3.setTotalAmount(Double.valueOf("1000"));
        List<Expense> ExpenseList = new ArrayList();
        ExpenseList.add(e3);
        e3.setDescription("laptop");
        e3.setTotalAmount(Double.valueOf("2000"));
        Expense output = new EditExpenseService().updateDescriptionAndAmount(e3,e3.getDescription(),e3.getTotalAmount());
        Assert.assertTrue(e3.getDescription()==output.getDescription());
        Assert.assertTrue(e3.getTotalAmount()==output.getTotalAmount());
    }

    @org.junit.jupiter.api.Test
    void calculateBySplitEqually() {
        Expense e5 = new Expense();
        List<MemberExpense> MemberList=new ArrayList();
        MemberExpense m1 = new MemberExpense();
        MemberExpense m2 = new MemberExpense();
        MemberList.add(m1);
        MemberList.add(m2);
        e5.setTotalAmount(Double.valueOf("200"));
        double share = e5.getTotalAmount()/ MemberList.size();
        List<Expense> ExpenseList = new ArrayList();
        ExpenseList.add(e5);
        e5.setMemberExpenseList(MemberList);
        Double output = new EditExpenseService().calculateBySplitEqually(e5.getTotalAmount(), MemberList.size());
        Assert.assertTrue(output==share);
    }

}

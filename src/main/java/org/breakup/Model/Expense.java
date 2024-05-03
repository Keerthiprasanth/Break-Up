package org.breakup.Model;


import java.util.ArrayList;
import java.util.List;

public class Expense {

    private Long expenseId;
    private String description;
    private Double totalAmount;
    // 1=Equally; 2=By share; 3=By amount
    private Integer splitType;
    private List<MemberExpense> memberExpenseList = new ArrayList<MemberExpense>();
    private String paidBy;

    public Expense() {
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getSplitType() {
        return splitType;
    }

    public void setSplitType(Integer splitType) {
        this.splitType = splitType;
    }

    public List<MemberExpense> getMemberExpenseList() {
        return memberExpenseList;
    }

    public void setMemberExpenseList(List<MemberExpense> memberExpenseList) {
        this.memberExpenseList = memberExpenseList;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }
}

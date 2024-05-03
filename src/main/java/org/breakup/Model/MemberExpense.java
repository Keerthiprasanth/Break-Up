package org.breakup.Model;

public class MemberExpense {
    private Member member;
    private Double expense;

    // + for owed; - for payable
    private String paymentFlag;
    private int share;

    public MemberExpense() {
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MemberExpense{" +
                "member=" + member +
                ", expense=" + expense +
                ", share=" + share +
                '}';
    }

    public Double getExpense() {
        return expense;
    }

    public String getPaymentFlag() {
        return paymentFlag;
    }

    public void setPaymentFlag(String paymentFlag) {
        this.paymentFlag = paymentFlag;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }


}

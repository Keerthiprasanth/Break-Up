package org.breakup.Service;

public class SettleExpenseService {

    public static double settleup(double total, double payment)
    {
        double final_amount = total-payment;
        return final_amount;
    }

    public static double balance(double owed, double payable)
    {
        double totalBalance = owed-payable;
        return totalBalance;
    }
}

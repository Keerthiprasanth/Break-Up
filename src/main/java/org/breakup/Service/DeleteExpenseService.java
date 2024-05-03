package org.breakup.Service;

import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;

public class DeleteExpenseService {
        public static double updateValues(double ExistingBalance, double ExpenseAmount)
        {
            if (ExistingBalance != 0 && ExpenseAmount!= 0)
            {
                double final_amount = ExistingBalance - ExpenseAmount;
                return final_amount;
            }
            else{
                return ExistingBalance;
            }

        }
        public static double balance(double Totalowed, double TotalPayable)
        {
            double totalBalance = Totalowed - TotalPayable ;
            return totalBalance;
        }
}

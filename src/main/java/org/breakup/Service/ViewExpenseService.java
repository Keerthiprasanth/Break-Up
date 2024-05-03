package org.breakup.Service;
import org.breakup.Model.Expense;
import org.breakup.Model.ExpenseList;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import java.util.ArrayList;
import java.util.List;
public class ViewExpenseService {
    private String loggedInMember = "rms@student.le.ac.uk";
    public ViewExpenseService() {
    }

    //    public List<Expense> getOwedList() {
//       return getOwedList();
//    }
    public void setOwedList(List<Expense> owedList) {
    }

    public List<Expense> getPayableList() {
        return getPayableList();
    }

    public void setPayableList(List<Expense> payableList) {
    }

    public class ExpenseList {
        private List<Expense> owedList;
        private List<Expense> payableList;
        public List<Expense> getOwedList() {
            return owedList;
        }

        public void setOwedList(List<Expense> owedList) {
            this.owedList = owedList;
        }

        public List<Expense> getPayableList() {
            return payableList;
        }

        public void setPayableList(List<Expense> payableList) {
            this.payableList = payableList;
        }
    }
    ExpenseList els=new ExpenseList();
    public void viewLoggedInDues(Member member) {
        loggedInMember = member.getEmailId();
    }

    public ExpenseList getExpensesWithMembers(List<Expense> expenses) {

        ExpenseList expenseList = new ExpenseList();
        List<Expense> owedList = new ArrayList<>();
        List<Expense> payableList = new ArrayList<>();
//            for (Expense expense : expenses)
//                for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
//                    if (memberExpense.getMember().getEmailId().equals(loggedInMember) && memberExpense.getPaymentFlag() != null) {
//                        if (memberExpense.getPaymentFlag().equals("+")) {
//                           // return owedList;
//                            owedList.add(expense);
//                        } else {
//                            //return payableList;
//                             payableList.add(expense);
//                        }
//                    }
//
//                }
        expenseList.setOwedList(owedList);
        expenseList.setPayableList(payableList);
        return expenseList;
    }

}
package org.breakup.Service;

import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;

import java.util.List;
import java.util.regex.Pattern;

public class CreateExpenseService {
    private String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public CreateExpenseService() {
    }

    public boolean validateTotalMembers(int totalMembers) {
        return totalMembers > 1;
    }

    public boolean validateEmailPattern(String emailId) {
        return Pattern.matches(this.emailRegex, emailId);
    }

    public Member checkForRegisteredUser(List<Member> membersList, String emailId) {
        if (membersList.size() > 0) {
            for (Member member : membersList) {
                if (member != null) {
                    if (member.getEmailId() != null && member.getEmailId().equals(emailId)) {
                        return member;
                    }
                }
            }
        }
        return null;
    }

    public int checkForPayerId(List<MemberExpense> memberExpenseList, String emailId) {
        int count = 0;
        for (MemberExpense memberExpense: memberExpenseList) {
            if (memberExpense != null) {
                if (memberExpense.getMember() != null) {
                    if (memberExpense.getMember().getEmailId() != null) {
                        if (memberExpense.getMember().getEmailId().equals(emailId)) {
                            count++;
                        }
                    }
                }
            }

        }
        return count;
    }

    public boolean checkForSplitType(int splitType) {
        return splitType != 1 && splitType != 2 && splitType != 3;
    }

    public void setPaymentFlag(Expense expense) {
        if (expense != null) {
            if (expense.getMemberExpenseList() != null) {
                if (expense.getMemberExpenseList().size() > 0) {
                    for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
                        if (memberExpense.getMember().getEmailId().equals(expense.getPaidBy())) {
                            memberExpense.setPaymentFlag("+");
                        } else {
                            memberExpense.setPaymentFlag("-");
                        }
                    }
                }
            }
        }
    }

    public void setExpenseEqually(Expense expense) {
        if (expense != null) {
            if (expense.getMemberExpenseList() != null && expense.getMemberExpenseList().size() > 0) {
                for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
                    memberExpense.setExpense(expense.getTotalAmount() / expense.getMemberExpenseList().size());
                }
            }
        }
    }

    public boolean validateShareValue(int share) {
        return share > -1;
    }

    public boolean validateExpenseValue(double expense) {
        return expense < 0.0;
    }

    public void calculateByShare(Expense expense) {
        int totalShares = 0;
        if (expense.getMemberExpenseList() != null && expense.getMemberExpenseList().size() > 0) {
            for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
                totalShares += memberExpense.getShare();
            }

            for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
                memberExpense.setExpense(expense.getTotalAmount() / totalShares * memberExpense.getShare());
            }
        }
    }

    public boolean validateTotalExpenseByMemberExpense(Expense expense) {
        Double sum = 0.0;
        if (expense.getMemberExpenseList() != null && expense.getMemberExpenseList().size() > 0) {
            for (MemberExpense memberExpense : expense.getMemberExpenseList()) {
                if (memberExpense.getExpense() != null) {
                    sum += memberExpense.getExpense();
                } else {
                    return true;
                }
            }
        }

        if (expense.getTotalAmount() != null) {
            return Math.round(sum * 100.0) / 100.0 != Math.round(expense.getTotalAmount() * 100.0) / 100.0;
        } else {
            return true;
        }
    }

    public void updateMemberValues(Member member, Expense expense, MemberExpense memberExpense) {
        if (member.getTotalBalance() == null) {
            member.setTotalBalance(0.0);
        }
        if (member.getTotalOwed() == null) {
            member.setTotalOwed(0.0);
        }
        if (member.getTotalPayable() == null) {
            member.setTotalPayable(0.0);
        }
        if (memberExpense.getExpense() != null) {
            if (memberExpense != null && memberExpense.getMember() != null && memberExpense.getMember().getEmailId() != null && memberExpense.getMember().getEmailId().equals(expense.getPaidBy())) {
                member.setTotalBalance(member.getTotalBalance() + memberExpense.getExpense());
                member.setTotalOwed(member.getTotalOwed() + memberExpense.getExpense());
            } else {
                member.setTotalBalance(member.getTotalBalance() - memberExpense.getExpense());
                member.setTotalOwed(member.getTotalPayable() + memberExpense.getExpense());
            }
        }
    }
}

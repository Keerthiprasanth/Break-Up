package org.breakup.Service;

import org.breakup.Model.Expense;
import org.breakup.Model.Member;
import org.breakup.Model.MemberExpense;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;

class CreateExpenseServiceTest {

    CreateExpenseService service = new CreateExpenseService();

    @Test
    void validateTotalMembers() {
        assertTrue(!service.validateTotalMembers(0));
        assertTrue(!service.validateTotalMembers(1));
        assertTrue(service.validateTotalMembers(2));
    }

    @Test
    void validateEmailPattern() {
        assertTrue(service.validateEmailPattern("eg300@student.le.ac.uk"));
        assertTrue(!service.validateEmailPattern("eg300@student."));
    }

    @Test
    void checkForRegisteredUser() {
        List<Member> membersList = new ArrayList();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        membersList.add(m1);
        Member m2 = new Member();
        m2.setEmailId("rms@student.le.ac.uk");
        membersList.add(m2);
        assertEquals(service.checkForRegisteredUser(membersList, "eg300@student.le.ac.uk"), m1);
        assertNotEquals(service.checkForRegisteredUser(membersList, "test@student.le.ac.uk"), m1);
        assertNotEquals(service.checkForRegisteredUser(membersList, "test@student.le.ac.uk"), m2);
    }

    @Test
    void checkForPayerId() {
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense = new MemberExpense();
        Member member = new Member();
        member.setEmailId("eg300@student.le.ac.uk");
        memberExpense.setMember(member);
        memberExpenseList.add(memberExpense);
        assertEquals(service.checkForPayerId(memberExpenseList, "eg300@student.le.ac.uk"), 1);
        assertNotEquals(service.checkForPayerId(memberExpenseList, "test@student.le.ac.uk"), 1);
    }

    @Test
    void checkForSplitType() {
        assertTrue(service.checkForSplitType(0));
        assertTrue(!service.checkForSplitType(1));
        assertTrue(!service.checkForSplitType(2));
        assertTrue(!service.checkForSplitType(3));
        assertTrue(service.checkForSplitType(4));
    }

    @Test
    void setPaymentFlag() {
        Expense e1 = new Expense();
        e1.setPaidBy("eg300@student.le.ac.uk");
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense1 = new MemberExpense();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        memberExpense1.setMember(m1);
        memberExpenseList.add(memberExpense1);
        MemberExpense memberExpense2 = new MemberExpense();
        Member m2 = new Member();
        m2.setEmailId("rms@student.le.ac.uk");
        memberExpense2.setMember(m2);
        memberExpenseList.add(memberExpense2);
        e1.setMemberExpenseList(memberExpenseList);
        service.setPaymentFlag(e1);
        assertTrue((e1.getMemberExpenseList().get(0)).getPaymentFlag() == "+");
        assertTrue((e1.getMemberExpenseList().get(1)).getPaymentFlag() == "-");
    }

    @Test
    void setExpenseEqually() {
        Expense e = new Expense();
        e.setPaidBy("eg300@student.le.ac.uk");
        e.setTotalAmount(20.0);
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense1 = new MemberExpense();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        memberExpense1.setMember(m1);
        memberExpenseList.add(memberExpense1);
        MemberExpense memberExpense2 = new MemberExpense();
        Member m2 = new Member();
        m2.setEmailId("rms@student.le.ac.uk");
        memberExpense2.setMember(m2);
        memberExpenseList.add(memberExpense2);
        e.setMemberExpenseList(memberExpenseList);
        service.setExpenseEqually(e);
        assertTrue((e.getMemberExpenseList().get(0)).getExpense() == 10.0);
        assertFalse((e.getMemberExpenseList().get(0)).getExpense() == 11.0);
        assertTrue((e.getMemberExpenseList().get(1)).getExpense() == 10.0);
        assertFalse((e.getMemberExpenseList().get(1)).getExpense() == 11.0);
    }

    @Test
    void validateShareValue() {
        assertTrue(!service.validateShareValue(-2));
        assertTrue(!service.validateShareValue(-1));
        assertTrue(service.validateShareValue(0));
        assertTrue(service.validateShareValue(1));
    }

    @Test
    void validateExpenseValue() {
        assertTrue(service.validateExpenseValue(-1.0));
        assertTrue(!service.validateExpenseValue(0.0));
        assertTrue(!service.validateExpenseValue(1.0));
    }

    @Test
    void calculateByShare() {
        Expense e = new Expense();
        e.setPaidBy("eg300@student.le.ac.uk");
        e.setTotalAmount(30.0);
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense1 = new MemberExpense();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        memberExpense1.setMember(m1);
        memberExpense1.setShare(1);
        memberExpenseList.add(memberExpense1);
        MemberExpense memberExpense2 = new MemberExpense();
        Member m2 = new Member();
        m1.setEmailId("rms@student.le.ac.uk");
        memberExpense2.setMember(m2);
        memberExpense2.setShare(2);
        memberExpenseList.add(memberExpense2);
        e.setMemberExpenseList(memberExpenseList);
        service.calculateByShare(e);
        assertTrue((e.getMemberExpenseList().get(0)).getExpense() == 10.0);
        assertFalse((e.getMemberExpenseList().get(0)).getExpense() == 11.0);
        assertTrue((e.getMemberExpenseList().get(1)).getExpense() == 20.0);
        assertFalse((e.getMemberExpenseList().get(1)).getExpense() == 11.0);
    }

    @Test
    void validateTotalExpenseByMemberExpense() {
        Expense e = new Expense();
        e.setPaidBy("eg300@student.le.ac.uk");
        e.setTotalAmount(30.0);
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense1 = new MemberExpense();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        memberExpense1.setMember(m1);
        memberExpense1.setExpense(10.0);
        memberExpenseList.add(memberExpense1);
        MemberExpense memberExpense2 = new MemberExpense();
        Member m2 = new Member();
        m1.setEmailId("rms@student.le.ac.uk");
        memberExpense2.setMember(m2);
        memberExpense2.setExpense(20.0);
        memberExpenseList.add(memberExpense2);
        e.setMemberExpenseList(memberExpenseList);
        assertTrue(!service.validateTotalExpenseByMemberExpense(e));
        memberExpense2.setExpense(null);
        assertTrue(service.validateTotalExpenseByMemberExpense(e));
        e.setTotalAmount(20.0);
        assertTrue(service.validateTotalExpenseByMemberExpense(e));
        memberExpense2.setExpense(20.0);
        e.setTotalAmount(null);
        assertTrue(service.validateTotalExpenseByMemberExpense(e));
    }

    @Test
    void updateMemberValues() {
        Expense e = new Expense();
        e.setPaidBy("eg300@student.le.ac.uk");
        e.setTotalAmount(30.0);
        List<MemberExpense> memberExpenseList = new ArrayList();
        MemberExpense memberExpense1 = new MemberExpense();
        Member m1 = new Member();
        m1.setEmailId("eg300@student.le.ac.uk");
        memberExpense1.setMember(m1);
        memberExpense1.setExpense(10.0);
        memberExpenseList.add(memberExpense1);
        MemberExpense memberExpense2 = new MemberExpense();
        Member m2 = new Member();
        m2.setEmailId("rms@student.le.ac.uk");
        memberExpense2.setMember(m2);
        memberExpense2.setExpense(20.0);
        memberExpenseList.add(memberExpense2);
        e.setMemberExpenseList(memberExpenseList);
        service.updateMemberValues(m1, e, memberExpense1);
        service.updateMemberValues(m2, e, memberExpense2);
        assertTrue(m1.getTotalBalance() == 10.0);
        assertTrue(m1.getTotalOwed() == 10.0);
        assertTrue(m1.getTotalPayable() == 0.0);
        assertTrue(m2.getTotalBalance() == -20.0);
    }
}
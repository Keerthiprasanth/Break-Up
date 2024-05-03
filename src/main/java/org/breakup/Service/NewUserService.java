package org.breakup.Service;
import org.breakup.Model.Member;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class NewUserService {

    public boolean nameValidation(String name) {
        String nameRegex = "(?=.{2,30}$)\\p{Lu}\\p{L}+(?:\\s\\p{Lu}\\p{L}+)*";
        return Pattern.matches(nameRegex, name);
    }

    public boolean phoneNoValidation(String phoneNo) {
        String phoneNoRegex = "(?:(?:(?:\\+|00)44[\\s\\-\\.]?)?(?:(?:\\(?0\\)?)[\\s\\-\\.]?)?(?:\\d[\\s\\-\\.]?){10})|(?=\\(?\\d*\\)?[\\x20\\-\\d]*)(\\(?\\)?\\x20*\\-*\\d){11}";
        return Pattern.matches(phoneNoRegex, phoneNo);
    }

    public boolean emailIdValidation(String emailId) {
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        return Pattern.matches(emailRegex, emailId);
    }

    public boolean passwordValidation(String password) {
        String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
        return Pattern.matches(passwordRegex, password);
    }

    public Member checkUserPresence(List<Member> membersList, String emailId) {
        Iterator<Member> var = membersList.iterator();
        Member member;
        do {
            if (!var.hasNext()) {
                return null;
            }
            member = (Member)var.next();
        } while(member.getEmailId() == null || !member.getEmailId().equals(emailId));
        return member;
    }

    public boolean validateMemberPassword(String password, Member member) {
        return !password.equals(member.getPassword());
    }
}
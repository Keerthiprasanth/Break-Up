package org.breakup.Service;

import org.breakup.Model.Member;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewUserServiceTest {

    NewUserService service = new NewUserService();

    @Test
    void nameValidation() {
        assertTrue(service.nameValidation("Steve"));
        assertTrue(service.nameValidation("Raju"));
        assertFalse(service.nameValidation("ra"));
        assertFalse(service.nameValidation("dgyiwhferufgorf"));
        assertFalse(service.nameValidation("jacob"));
    }
    @org.junit.jupiter.api.Test
    void phoneNoValidation() {
        assertTrue(service.phoneNoValidation("07865126458"));
        assertTrue(service.phoneNoValidation("07759061678"));
        assertFalse(service.phoneNoValidation("634827"));
        assertFalse(service.phoneNoValidation("349653453450435647348"));
        assertFalse(service.phoneNoValidation("wdshfiesd"));
    }

    @org.junit.jupiter.api.Test
    void emailIdValidation() {
        assertTrue(service.emailIdValidation("prasanth@gmail.com"));
        assertTrue(service.emailIdValidation("ram@yahoo.com"));
        assertTrue(service.emailIdValidation("kpr11@student.le.ac.uk"));
        assertFalse(service.emailIdValidation("prasanth"));
        assertFalse(service.emailIdValidation("rajcom@"));
    }

    @org.junit.jupiter.api.Test
    void passwordValidation() {
        assertTrue(service.passwordValidation("Raju@14627"));
        assertTrue(service.passwordValidation("Harry56@36"));
        assertFalse(service.passwordValidation("43"));
        assertFalse(service.passwordValidation("adoweifherofsbeosdopdisfcbdifd"));
        assertFalse(service.passwordValidation("steve"));
    }

    @Test
    void checkUserPresence() {
        List<Member> membersList = new ArrayList();
        Member m = new Member();
        m.setEmailId("kishor@gmail.com");
        membersList.add(m);
        assertEquals(service.checkUserPresence(membersList, "kishor@gmail.com"), m);
        assertNotEquals(service.checkUserPresence(membersList, "raju@student.le.ac.uk"), m);
        assertNotEquals(service.checkUserPresence(membersList, "maddy@gyahoo.com"), m);
    }

    @org.junit.jupiter.api.Test
    void validateMemberPassword() {
        List<Member> memberList = new ArrayList<>();
        Member m = new Member();
        m.setPassword("Raja@4546");
        memberList.add(m);
        assertFalse(service.validateMemberPassword("Raja@4546",m));
        assertTrue(service.validateMemberPassword("gfwejwfyy",m));
        assertTrue(service.validateMemberPassword("238768336",m));
    }
}
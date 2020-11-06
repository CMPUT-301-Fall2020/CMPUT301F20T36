package com.example.book_master;

import com.example.book_master.models.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for User
 * Notice functionality related to Firebase could not be tested (it could not connect to cloud)
 */
public class UserTest {
    private User mockUser() {
        final String email = "lyon233333@gmail.com";
        final String password = "ntd10086";
        final String username = "Shrike";
        final String contactInfo = "QAQ";
        return new User(email, password, username, contactInfo);
    }

    /**
     * Test:    getEmail(), getPassword(), getUsername(), setUsername(String username),
     *          getContactInfo(), setContactInfo(String contactInfo)
     */
    @Test
    void testSetGet() {
        User temp = mockUser();

        assertTrue(temp.getEmail().equalsIgnoreCase("lyon233333@gmail.com"));
        assertTrue(temp.getPassword().equalsIgnoreCase("ntd10086"));
        assertTrue(temp.getUsername().equalsIgnoreCase("Shrike"));
        assertTrue(temp.getContactInfo().equalsIgnoreCase("QAQ"));

        temp.setUsername("(￣ε(#￣)☆");
        temp.setContactInfo("ヽ(✿ﾟ▽ﾟ)ノ");
        assertTrue(temp.getUsername().equalsIgnoreCase("(￣ε(#￣)☆"));
        assertTrue(temp.getContactInfo().equalsIgnoreCase("ヽ(✿ﾟ▽ﾟ)ノ"));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.prog5121;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lwane
 */
public class PROG5121Test {
    
    public PROG5121Test() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
   

    /**
     * Test of checkUserName method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        String username = "kyl_1";
        boolean expResult = true;
        boolean result = PROG5121.checkUserName(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of checkPasswordComplexity method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        String password = "ch&&sec@ke99!";
        boolean expResult = true;
        boolean result = PROG5121.checkPasswordComplexity(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of checkCellPhoneNumber method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testCheckCellPhoneNumber() {
        System.out.println("checkCellPhoneNumber");
        String number = "+27838968976";
        boolean expResult = true;
        boolean result = PROG5121.checkCellPhoneNumber(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String expResult = "Username and password successfully captured. User registered";
        String result = PROG5121.registerUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String username = "kyl_1";
        String password = "ch&&sec@ke99!";
        String storedUsername = "kyl_1";
        String storedPassword = "ch&&sec@ke99!";
        boolean expResult = true;
        boolean result = PROG5121.loginUser(username, password, storedUsername, storedPassword);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       //True("The test case is a prototype.");
    }

    /**
     * Test of returnLoginStatus method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean status = true;
        String expResult = "Login successful! Welcome back kyl_1!";
        String result = PROG5121.returnLoginStatus(status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of main method, of class PROG5121.
     */
    @org.junit.jupiter.api.Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        PROG5121.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of sendMessage method, of class PROG5121.
     */
    @Test
    public void testSendMessage() {
        System.out.println("sendMessage");
        PROG5121.sendMessage();
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of saveMessagesToJSON method, of class PROG5121.
     */
    @Test
    public void testSaveMessagesToJSON() {
        System.out.println("saveMessagesToJSON");
        PROG5121.saveMessagesToJSON();
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }

    /**
     * Test of showRecentlySentMessages method, of class PROG5121.
     */
    @Test
    public void testShowRecentlySentMessages() {
        System.out.println("showRecentlySentMessages");
        PROG5121.showRecentlySentMessages();
        // TODO review the generated test code and remove the default call to fail.
        //True("The test case is a prototype.");
    }
    
}

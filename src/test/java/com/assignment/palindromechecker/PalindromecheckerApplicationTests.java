package com.assignment.palindromechecker;

import com.assignment.palindromechecker.model.PalindromeChecker;
import com.assignment.palindromechecker.service.PalindromeService;
import com.assignment.palindromechecker.validation.PalindromeValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PalindromecheckerApplicationTests {

    @Autowired
    private PalindromeService palindromeService;

    @Autowired
    private PalindromeValidator palindromeValidator;

    @Test
    void contextLoads() {
    }

    @Test
    public void mainTest() {
        PalindromecheckerApplication.main(new String[] {});
    }

    private PalindromeChecker getPalindromeCheckerRequest(String text) {
        PalindromeChecker palindromeChecker = new PalindromeChecker();
        palindromeChecker.setUsername("TestUser1");
        palindromeChecker.setText(text);
        return palindromeChecker;
    }

    @Test
    void testValidPalindrome() {
        assertTrue(palindromeService.isPalindrome(getPalindromeCheckerRequest("Madam")));
        assertTrue(palindromeService.isPalindrome(getPalindromeCheckerRequest("KayAK")));
    }


    @Test
    void testInvalidPalindrome() {
        assertFalse(palindromeService.isPalindrome(getPalindromeCheckerRequest("123abc")));
        assertFalse(palindromeService.isPalindrome(getPalindromeCheckerRequest(" abc")));
    }

    @Test
    void testPalindromeValidInputs() {
        assertFalse(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest("madam")));
        assertFalse(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest("racecar")));
        assertFalse(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest("Kayak")));
        assertFalse(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest("madam123")));
    }

    @Test
    void testPalindromeInvalidInputs() {
        assertTrue(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest("123abc")));
        assertTrue(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest(" hello")));
        assertTrue(palindromeValidator.isInValidPalindromeInput(getPalindromeCheckerRequest(" ")));
    }

}

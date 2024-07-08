package com.assignment.palindromechecker.validation;

import com.assignment.palindromechecker.model.PalindromeChecker;
import org.springframework.stereotype.Component;

@Component
public class PalindromeValidator {

    /**
     * Method is used to validate the input text
     *
     * @param palindromeChecker
     * @return boolean
     */
    public boolean isInValidPalindromeInput(PalindromeChecker palindromeChecker) {
        if (palindromeChecker.getUsername() == null || palindromeChecker.getText() == null || !palindromeChecker.getText().matches("^(?![0-9 ])[A-Za-z0-9 ]+$")) {
            return true;
        }
        return false;
    }
}

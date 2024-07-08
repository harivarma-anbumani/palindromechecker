package com.assignment.palindromechecker.controller;

import com.assignment.palindromechecker.model.PalindromeChecker;
import com.assignment.palindromechecker.service.PalindromeService;
import com.assignment.palindromechecker.validation.PalindromeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PalindromeController {

    @Autowired
    private PalindromeService palindromeService;

    @Autowired
    private PalindromeValidator palindromeValidator;

    @PostMapping("/checkPalindrome")
    public ResponseEntity<Object> checkPalindrome(@RequestBody PalindromeChecker palindromeChecker) {
        try {
            if (palindromeValidator.isInValidPalindromeInput(palindromeChecker)) {
                return ResponseEntity.badRequest().body("Invalid User Request: Only Non-Empty and alphanumeric characters are allowed as inputs");
            }
            palindromeChecker.setPalindrome(palindromeService.isPalindrome(palindromeChecker));
            return ResponseEntity.ok(palindromeChecker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

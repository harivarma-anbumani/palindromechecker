package com.assignment.palindromechecker;

import com.assignment.palindromechecker.controller.PalindromeController;
import com.assignment.palindromechecker.model.PalindromeChecker;
import com.assignment.palindromechecker.service.PalindromeService;
import com.assignment.palindromechecker.validation.PalindromeValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PalindromeController.class)
public class PalindromeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PalindromeService palindromeService;

    @MockBean
    private PalindromeValidator palindromeValidator;

    @Autowired
    private ObjectMapper objectMapper;

    private PalindromeChecker validPalindromeChecker;
    private PalindromeChecker invalidPalindromeChecker;

    @BeforeEach
    public void setUp() {
        validPalindromeChecker = new PalindromeChecker();
        validPalindromeChecker.setUsername("validTestUser");
        validPalindromeChecker.setText("madam");

        invalidPalindromeChecker = new PalindromeChecker();
        invalidPalindromeChecker.setUsername("invalidTestUser");
        invalidPalindromeChecker.setText("123 madam");
    }

    @Test
    public void testCheckPalindrome_ValidInput() throws Exception {
        when(palindromeValidator.isInValidPalindromeInput(any(PalindromeChecker.class))).thenReturn(false);
        when(palindromeService.isPalindrome(any(PalindromeChecker.class))).thenReturn(true);

        mockMvc.perform(post("/api/checkPalindrome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPalindromeChecker)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckPalindrome_InvalidInput() throws Exception {
        when(palindromeValidator.isInValidPalindromeInput(any(PalindromeChecker.class))).thenReturn(true);

        mockMvc.perform(post("/api/checkPalindrome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPalindromeChecker)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid User Request: Only Non-Empty and alphanumeric characters are allowed as inputs"));
    }

    @Test
    public void testCheckPalindrome_ExceptionHandling() throws Exception {
        when(palindromeValidator.isInValidPalindromeInput(any(PalindromeChecker.class))).thenThrow(new IllegalArgumentException("Unexpected error"));

        mockMvc.perform(post("/api/checkPalindrome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPalindromeChecker)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unexpected error"));
    }
}


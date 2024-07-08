package com.assignment.palindromechecker;

import com.assignment.palindromechecker.model.PalindromeChecker;
import com.assignment.palindromechecker.service.PalindromeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@EnableAsync
@ActiveProfiles("test")
public class PalindromeServiceTest {

    @InjectMocks
    private PalindromeService palindromeService;

    @Mock
    private Executor executor;

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        tempFile = Files.createTempFile("palindromeTestExecutionFile", ".txt");
        try {
            PalindromeService.class.getDeclaredField("STORAGE_FILE").setAccessible(true);
            PalindromeService.class.getDeclaredField("STORAGE_FILE").set(null, tempFile.toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private PalindromeChecker getPalindromeCheckerRequest(String text) {
        PalindromeChecker palindromeChecker = new PalindromeChecker();
        palindromeChecker.setUsername("TestUser1");
        palindromeChecker.setText(text);
        return palindromeChecker;
    }


    @Test
    public void testAddPalindromeTextToFile() throws IOException {
        String text = "racecar";
        palindromeService.isPalindrome(getPalindromeCheckerRequest(text));

        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile.toFile()))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.equals(text)) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(palindromeService.isPalindrome(getPalindromeCheckerRequest("racecar")));
    }

}

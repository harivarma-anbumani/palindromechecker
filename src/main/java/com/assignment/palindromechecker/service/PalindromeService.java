package com.assignment.palindromechecker.service;

import com.assignment.palindromechecker.model.PalindromeChecker;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PalindromeService {

    private static final String STORAGE_FILE = "matchedPalindromeValues.txt";

    private static final ConcurrentHashMap<String, Integer> cacheMap = new ConcurrentHashMap<>();

    private static final Set<String> cache = ConcurrentHashMap.newKeySet();

    @PostConstruct
    public void initializeCache() throws IOException {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cache.add(line);
                }
            }
        }
    }

    /**
     * @param palindromeChecker
     * @return boolean
     */
    public boolean isPalindrome(PalindromeChecker palindromeChecker) {

        palindromeChecker.setText(palindromeChecker.getText().toLowerCase());

        if (cache.contains(palindromeChecker.getText())) {
            System.out.println("-------------");
            return true;
        }
        boolean result = palindromeChecker.getText().equalsIgnoreCase(new StringBuilder(palindromeChecker.getText()).reverse().toString());

        if (result) {
            addPalindromeTextToFile(palindromeChecker.getText());
        }
        return result;
    }

    @Async
    private void addPalindromeTextToFile(String text) {
        cache.add(text);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE_FILE, true))) {
            writer.write(text);
            writer.newLine();
        } catch (IOException e) {
            e.getMessage();
        }
    }
}

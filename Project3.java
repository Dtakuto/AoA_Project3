import java.io.*;
import java.util.*;

public class Project3 {
    public static void main(String[] args) {
        // Load the dictionary
        Set<String> dictionary = loadDictionary("aliceInWonderlandDictionary.txt");

        // Process each input string
        processInput("input.txt", dictionary);
    }

    // Load dictionary words into a HashSet
    public static Set<String> loadDictionary(String filename) {
        Set<String> dictionary = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
        return dictionary;
    }

    // Read input file line by line and process each string
    public static void processInput(String filename, Set<String> dictionary) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Skip empty lines if any
                Result result = splitIntoWords(line, dictionary);
                if (result == null) {
                    System.out.println(line + " cannot be split into AiW words.");
                } else {
                    System.out.println(line + " can be split into " + result.numWords + " AiW words: " + String.join(" ", result.words));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    // Dynamic Programming function to split input string into minimum number of valid words
    public static Result splitIntoWords(String s, Set<String> dictionary) {
        int n = s.length();
        int[] dp = new int[n + 1]; // dp[i] = min number of words to split from position i
        int[] next = new int[n + 1]; // next[i] = next index to jump to after taking a valid word

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[n] = 0; // Base case: no words needed after the end of the string

        // Bottom-up DP filling
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j <= n; j++) {
                String substring = s.substring(i, j);
                if (dictionary.contains(substring) && dp[j] != Integer.MAX_VALUE) {
                    if (1 + dp[j] < dp[i]) {
                        dp[i] = 1 + dp[j];
                        next[i] = j;
                    }
                }
            }
        }

        // If we couldn't split starting from position 0
        if (dp[0] == Integer.MAX_VALUE) {
            return null;
        }

        // Reconstruct the list of words
        List<String> words = new ArrayList<>();
        int i = 0;
        while (i < n) {
            words.add(s.substring(i, next[i]));
            i = next[i];
        }

        return new Result(dp[0], words);
    }
}

// Helper class to store results cleanly
class Result {
    int numWords;
    List<String> words;

    public Result(int numWords, List<String> words) {
        this.numWords = numWords;
        this.words = words;
    }
}
import java.util.*;
import java.io.*;

public class Project3 {
    static Set<String> dic = new HashSet<>();

    public static void main(String[] args){
        try {
            loadDictionary("aliceInWonderlandDictionary.txt");
            processFile("input.txt");
        }
        catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void loadDictionary(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line; 

        while ((line = reader.readLine()) != null) {
            String word = line.trim();
            dic.add(word);
        }
        reader.close();

    }

    static void processFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line; 

        while ((line = reader.readLine()) != null) {
            readFileLines(line.trim());
        }
        reader.close();

        System.err.println("Words read from file: " + fileName);
    }

    static void readFileLines(String line) {
        int n = line.length();
        int[] dp = new int[n + 1];
        int[] prev = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // Base case: empty string

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                String substring = line.substring(j, i);
                if (dic.contains(substring)) {
                    if (dp[j] != Integer.MAX_VALUE && dp[j] + 1 < dp[i]) {
                        dp[i] = dp[j] + 1;
                        prev[i] = j;
                    }
                }
            }
        }

        if (dp[n] == Integer.MAX_VALUE) {
            System.out.println(line + " cannot be split into AiW words.");
        } else {
            List<String> words = new ArrayList<>();
            int idx = n;
            while (idx > 0) {
                int j = prev[idx];
                words.add(line.substring(j, idx));
                idx = j;
            }
            Collections.reverse(words);
            System.out.println(line + " can be split into " + words.size() + " AiW words: " + String.join(" ", words));
        }
    }

}

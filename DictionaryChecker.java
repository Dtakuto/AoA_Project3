import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DictionaryChecker {
    public static void main(String[] args) {
        Set<String> dictionary = new HashSet<>();
        
        try {
            // Read dictionary file
            BufferedReader reader = new BufferedReader(new FileReader("aliceInWonderlandDictionary.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                dictionary.add(word);
            }
            reader.close();
            
            // Check if words are in the dictionary
            String[] words = {"alice", "tacos", "lice", "william", "handsome", "aliceinwonderland"};
            for (String word : words) {
                if (dictionary.contains(word)) {
                    System.out.println(word + " is in the dictionary.");
                } else {
                    System.out.println(word + " is not in the dictionary.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }
    }
}
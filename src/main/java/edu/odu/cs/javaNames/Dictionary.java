package edu.odu.cs.javaNames;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

public class Dictionary {

    private HashSet<String> words;

    public Dictionary() throws IOException {
        words = new HashSet<>();

        InputStream wordsIn = Dictionary.class.getResourceAsStream("/edu/odu/cs/javaNames/words.txt");
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(wordsIn, StandardCharsets.UTF_8))
        ) {
            String word = in.readLine();
            while (word != null) {
                words.add(word.toLowerCase());
                word = in.readLine();
            }
        }
    }

    public boolean contains(String string) {
        return words.contains(string.toLowerCase());
    }

}

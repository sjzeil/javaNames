package edu.odu.cs.javaNames;

import java.util.ArrayList;
import java.util.List;

public class Identifier {

    String identifier;

    public Identifier(String string) {
        identifier = string;
    }

    public String toString() {
        return identifier;
    }

    public List<String> dissect() {
        boolean allUpper = hasNoLowerCase(identifier);
        List<String> results = new ArrayList<>();
        StringBuilder partial = new StringBuilder();
        for (int i = 0; i < identifier.length(); ++i) {
            char c = identifier.charAt(i);
            if (c == '_' || ! Character.isAlphabetic(c)) {
                if (partial.length() > 0) {
                    addToResults(partial.toString(), results);
                    partial = new StringBuilder();
                }
            } else if ((!allUpper) && Character.isUpperCase(c)) {
                if (partial.length() > 0) {
                    addToResults(partial.toString(), results);
                    partial = new StringBuilder();
                }
                partial.append(c);
            } else {
                partial.append(c);
            }
        }
        if (partial.length() > 0) {
            addToResults(partial.toString(), results);
        }
        return results;
    }

    final static int ACRONYM_LIMIT = 3;

    private void addToResults(String string, List<String> results) {
        if (string.length() <= ACRONYM_LIMIT && hasNoLowerCase(string)) {
            for (int i = 0; i < string.length(); ++i) {
                results.add("" + string.charAt(i));
            }
        } else {
            results.add(string);
        }
    }

    private boolean hasNoLowerCase(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (Character.isAlphabetic(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

}

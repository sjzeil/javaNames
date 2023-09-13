package edu.odu.cs.javaNames;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static java.nio.file.FileVisitResult.CONTINUE;

public class CollectIdentifiers implements Iterable<String> {

    private Set<String> identifiers;

    public CollectIdentifiers() {
        identifiers = new HashSet<>();
    }

    public Iterator<String> iterator() {
        return identifiers.iterator();
    }

    public void collect(Reader input) throws IOException {
        Scanner scanner = new Scanner(input);
        int lastToken = Token.OTHER;
        Token t = scanner.yylex();
        while (t.kind != Token.EOF) {
            if (t.kind == Token.IDENTIFIER &&
                    (lastToken == Token.DECLARER
                            || lastToken == Token.IDENTIFIER)) {
                identifiers.add(t.lexeme);
            }
            lastToken = t.kind;
            t = scanner.yylex();
        }
    }

    public boolean contains(String identifier) {
        return identifiers.contains(identifier);
    }

    private class CollectJavaFiles extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attr) throws IOException {
            if (file.getFileName().toString().endsWith(".java")) {
                BufferedReader in = new BufferedReader(
                        new FileReader(file.toFile(), StandardCharsets.UTF_8));
                collect(in);
                in.close();
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir,
                IOException e) {
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException e) {
            return CONTINUE;
        }

    }

    public void collect(Path directoryTree) throws IOException {
        Files.walkFileTree(directoryTree, new CollectJavaFiles());
    }

}

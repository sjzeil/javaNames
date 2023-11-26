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

    /**
     * Read tokens, watching them via a finite automaton to recognize
     * declarations of variables, types, & constants.
     * 
     * Refer to identifierFSA.png
     * 
     * @param input input source
     * @throws IOException
     */
    public void collect(Reader input) throws IOException {
        Scanner scanner = new Scanner(input);
        int state = 0;
        Token t = scanner.yylex();
        while (t.kind != Token.EOF) {
            switch (state) {
                case 0:
                    if (t.kind == Token.IDENTIFIER)
                        state = 1;
                    else if (t.kind == Token.DECLARER)
                        state = 3;
                    else
                        state = 0;
                    break;
                case 1:
                    if (t.kind == Token.IDENTIFIER) {
                        state = 2;
                        identifiers.add(t.lexeme);
                        state = 0;
                    } else if (t.kind == Token.DECLARER)
                        state = 3;
                    else if (t.kind == Token.LESS)
                        state = 4;
                    else
                        state = 0;
                    break;
                case 3:
                    if (t.kind == Token.IDENTIFIER) {
                        state = 2;
                        identifiers.add(t.lexeme);
                        state = 0;
                    } else if (t.kind == Token.DECLARER)
                        state = 3;
                    else
                        state = 0;
                    break;
                case 4:
                    if (t.kind == Token.IDENTIFIER)
                        state = 6;
                    else if (t.kind == Token.DECLARER)
                        state = 6;
                    else if (t.kind == Token.GREATER)
                        state = 5;
                    else
                        state = 0;
                    break;
                case 5:
                    if (t.kind == Token.IDENTIFIER) {
                        state = 2;
                        identifiers.add(t.lexeme);
                        state = 0;
                    }
                    else if (t.kind == Token.DECLARER)
                        state = 3;
                    else
                        state = 0;
                    break;
                case 6:
                    if (t.kind == Token.COMMA)
                        state = 4;
                    else if (t.kind == Token.GREATER)
                        state = 5;
                    else
                        state = 0;
                    break;
                default:
                    state = 0;
            }
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


package edu.odu.cs.javaNames;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class IdentifiersTest {

    @Test
    public void testIdentifierConstructor() {
        Identifier ident = new Identifier("abc");
        assertThat (ident.toString(), is("abc"));
        List<String> parts = ident.dissect();
        assertThat(parts, contains("abc"));
    }

    @Test
    public void testIdentifierUnderScores() {
        String input = "abc_de_f";
        Identifier ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("abc", "de", "f"));
    }

    @Test
    public void testCamelCase() {
        String input = "abcDeF";
        Identifier ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("abc", "De", "F"));

        input = "AbcDeF";
        ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("Abc", "De", "F"));

    }

    @Test
    public void testAlphanumeric() {
        String input = "abc23DeF";
        Identifier ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("abc", "De", "F"));

        input = "AbcDeF0";
        ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("Abc", "De", "F"));

    }

    @Test
    public void testAcronyms() {
        String input = "abcDEF";
        Identifier ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("abc", "D", "E", "F"));

        input = "ABCDEF23";
        ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("ABCDEF"));

        input = "EOF2";   // Short all-upper-case are assumed to be acronyms
        ident = new Identifier(input);
        assertThat (ident.toString(), is(input));
        assertThat(ident.dissect(), contains("E", "O", "F"));


    }

}

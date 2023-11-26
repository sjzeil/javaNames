
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

public class CollectIdentifiersTest {

    @Test
    public void testCollectIdentifiersConstructor() {
        CollectIdentifiers ci = new CollectIdentifiers();
        ;
        Iterator<String> it = ci.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testCollectIdentifiersFromReader() throws IOException {
        String inputStr = "class A { int b() {} \n public A c() {} }";
        StringReader input = new StringReader(inputStr);

        CollectIdentifiers ci = new CollectIdentifiers();
        ci.collect(input);
        assertThat(ci.contains("A"), is(true));
        assertThat(ci.contains("a"), is(false));

        assertThat(ci, hasItems("A", "b", "c"));
    }

    @Test
    public void testCollectGenerics() throws IOException {
        String inputStr = "class A { Map<int,A> b() {} \n public A c() {} }";
        StringReader input = new StringReader(inputStr);

        CollectIdentifiers ci = new CollectIdentifiers();
        ci.collect(input);
        assertThat(ci.contains("A"), is(true));
        assertThat(ci.contains("a"), is(false));

        assertThat(ci, hasItems("A", "b", "c"));
    }


    @Test
    public void testCollectIdentifiersFromDirectoryTree() throws IOException {
        Path testDir = Paths.get("src", "test", "data");

        CollectIdentifiers ci = new CollectIdentifiers();
        ci.collect(testDir);

        String[] expected = {"TestLexer", "intDec", "longDec", "intHex",
            "longHex", "intOct", "longOc", "smallest", "main", "argv",
            "i", "scanner", "s", "e", "TestLexer2",
            "stringArray", "tlex"};

        assertThat(ci.contains("TestLexer"), is(true));
        assertThat(ci.contains("TestLexer3"), is(false));

        assertThat(ci, containsInAnyOrder(expected));
    }



}

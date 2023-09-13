
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

public class DictionaryTest {

    @Test
    public void testDictionary() throws IOException {
        Dictionary d = new Dictionary();
        assertThat(d.contains("I"), is(true));
        assertThat(d.contains("i"), is(true));
        assertThat(d.contains("with"), is(true));
        assertThat(d.contains("xyx"), is(false));
    }


}

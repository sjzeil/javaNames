
package edu.odu.cs.javaNames;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.io.StringReader;


public class JavaLexerTest {

    public final static int IDENTIFIER = 0;
    public final static int DECLARER = 1;
    public final static int OTHER = 2;
    public final static int EOF = 3;

  
  
  @Test
  public void lexTestBasic() throws Exception {
    String input = "class A { public int b() {int c; } \n}\n";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {DECLARER, IDENTIFIER, OTHER, OTHER, DECLARER, IDENTIFIER,
      OTHER, OTHER, OTHER, DECLARER, IDENTIFIER, OTHER, OTHER, OTHER, EOF};
    String[] expectedIdentifiers = {"A", "b", "c"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(EOF));
  }

@Test
  public void lexIllegalAndUnicodeChars() throws Exception {
    String input = "\\ '\u00A5'";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {OTHER, OTHER, EOF};
    String[] expectedIdentifiers = {};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(EOF));
  }


  @Test
  public void lexStringLiterals() throws Exception {
    String input = "\"A \" B \"/*\" C \"/*\"";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {OTHER, IDENTIFIER, OTHER, IDENTIFIER, OTHER, EOF};
    String[] expectedIdentifiers = {"B", "C"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(EOF));
  }

@Test
  public void lexComments() throws Exception {
    String input = "/* A \"B\" \n\n */ C ";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {IDENTIFIER, EOF};
    String[] expectedIdentifiers = {"C"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(EOF));
  }

@Test
  public void lexLineComments() throws Exception {
    String input = "// A \n B // C /* \n D // */";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {IDENTIFIER, IDENTIFIER, EOF};
    String[] expectedIdentifiers = {"B", "D"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(EOF));
  }


}

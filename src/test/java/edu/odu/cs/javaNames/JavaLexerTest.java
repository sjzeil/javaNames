
package edu.odu.cs.javaNames;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.io.StringReader;


public class JavaLexerTest {

  
  
  @Test
  public void lexTestBasic() throws Exception {
    String input = "class A { public int b() {int c; } \n}\n";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {Token.DECLARER, Token.IDENTIFIER, Token.OTHER, 
      Token.OTHER, Token.DECLARER, Token.IDENTIFIER,
      Token.OTHER, Token.OTHER, Token.OTHER, Token.DECLARER,
      Token.IDENTIFIER, Token.OTHER, Token.OTHER, Token.OTHER, Token.EOF};
    String[] expectedIdentifiers = {"A", "b", "c"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != Token.EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == Token.IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(Token.EOF));
  }

@Test
  public void lexIllegalAndUnicodeChars() throws Exception {
    String input = "\\ '\u00A5'";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {Token.OTHER, Token.OTHER, Token.EOF};
    String[] expectedIdentifiers = {};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != Token.EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == Token.IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(Token.EOF));
  }


  @Test
  public void lexStringLiterals() throws Exception {
    String input = "\"A \" B \"/*\" C \"/*\"";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {Token.OTHER, Token.IDENTIFIER, Token.OTHER, 
      Token.IDENTIFIER, Token.OTHER, Token.EOF};
    String[] expectedIdentifiers = {"B", "C"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != Token.EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == Token.IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(Token.EOF));
  }

@Test
  public void lexComments() throws Exception {
    String input = "/* A \"B\" \n\n */ C ";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {Token.IDENTIFIER, Token.EOF};
    String[] expectedIdentifiers = {"C"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != Token.EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == Token.IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(Token.EOF));
  }

@Test
  public void lexLineComments() throws Exception {
    String input = "// A \n B // C /* \n D // */";
    StringReader in = new StringReader(input);
    Scanner scanner = new Scanner(in);
    int[] expected = {Token.IDENTIFIER, Token.IDENTIFIER, Token.EOF};
    String[] expectedIdentifiers = {"B", "D"};
    Token t = scanner.yylex();
    int tokenNum = 0;
    int idNum = 0;
    while (t.kind != Token.EOF) {
      assertThat(t.kind, is(expected[tokenNum]));
      ++tokenNum;
      if (t.kind == Token.IDENTIFIER) {
        assertThat(t.lexeme, is(expectedIdentifiers[idNum]));
        ++idNum;
      }
      t = scanner.yylex();
    }
    assertThat(expected[tokenNum], is(Token.EOF));
  }


}

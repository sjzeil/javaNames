  package edu.odu.cs.javaNames;
  
  public class Token {
    public final static int IDENTIFIER = 0;
    public final static int DECLARER = 1;
    public final static int LESS = 2;
    public final static int GREATER = 3;
    public final static int COMMA = 4;
    public final static int OTHER = 5;
    public final static int EOF = 6;
    
    public String lexeme;
    public int kind;

    public Token (int theKind, String theLexeme) {
      kind = theKind;
      lexeme = theLexeme;
    }

  }

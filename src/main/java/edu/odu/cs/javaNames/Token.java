  package edu.odu.cs.javaNames;
  
  public class Token {
    public final static int IDENTIFIER = 0;
    public final static int DECLARER = 1;
    public final static int OTHER = 2;
    public final static int EOF = 3;
    
    public String lexeme;
    public int kind;

    public Token (int theKind, String theLexeme) {
      kind = theKind;
      lexeme = theLexeme;
    }

  }

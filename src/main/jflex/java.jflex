/*
 * Modifications (C) 2023 Steven Zeil, based on...
 */

/*
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>
 * SPDX-License-Identifier: GPL-2.0-only
 */


package edu.odu.cs.javaNames;

%%

%public
%class Scanner

%unicode

%line
%column

%type Token

%{
  /*
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

  }*/
  StringBuilder string = new StringBuilder();

  private Token symbol(int type) {
    return new Token(type, yytext());
  }

  private Token symbol(int type, String lexeme) {
    return new Token(type, "\"" + lexeme + '"');
  }

  private Token symbol() {
    return new Token(Token.OTHER, yytext());
  }


  /**
   * assumes correct representation of a long value for
   * specified radix in scanner buffer from <code>start</code>
   * to <code>end</code>
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} |
          {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
DecLongLiteral    = {DecIntegerLiteral} [lL]

HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexLongLiteral    = 0 [xX] 0* {HexDigit} {1,16} [lL]
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit} {1,15}
OctLongLiteral    = 0+ 1? {OctDigit} {1,21} [lL]
OctDigit          = [0-7]

/* floating point literals */
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+
Exponent = [eE] [+-]? [0-9]+

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state STRING, CHARLITERAL

%%

<YYINITIAL> {

  /* keywords */
  "abstract"                     { return symbol(); }
  "assert"                       { return symbol(); }
  "boolean"                      { return symbol(Token.DECLARER); }
  "break"                        { return symbol(); }
  "byte"                         { return symbol(Token.DECLARER); }
  "case"                         { return symbol(); }
  "catch"                        { return symbol(); }
  "char"                         { return symbol(Token.DECLARER); }
  "class"                        { return symbol(Token.DECLARER); }
  "const"                        { return symbol(); }
  "continue"                     { return symbol(); }
  "default"                      { return symbol(); }
  "do"                           { return symbol(); }
  "double"                       { return symbol(Token.DECLARER); }
  "else"                         { return symbol(); }
  "enum"                         { return symbol(); }
  "extends"                      { return symbol(); }
  "final"                        { return symbol(); }
  "finally"                      { return symbol(); }
  "float"                        { return symbol(Token.DECLARER); }
  "for"                          { return symbol(); }
  "goto"                         { return symbol(); }
  "if"                           { return symbol(); }
  "implements"                   { return symbol(); }
  "import"                       { return symbol(); }
  "instanceof"                   { return symbol(); }
  "int"                          { return symbol(Token.DECLARER); }
  "interface"                    { return symbol(Token.DECLARER); }
  "long"                         { return symbol(Token.DECLARER); }
  "native"                       { return symbol(); }
  "new"                          { return symbol(); }
  "package"                      { return symbol(); }
  "private"                      { return symbol(); }
  "protected"                    { return symbol(); }
  "public"                       { return symbol(); }
  "return"                       { return symbol(); }
  "short"                        { return symbol(Token.DECLARER); }
  "static"                       { return symbol(); }
  "strictfp"                     { return symbol(); }
  "super"                        { return symbol(); }
  "switch"                       { return symbol(); }
  "synchronized"                 { return symbol(); }
  "this"                         { return symbol(); }
  "throw"                        { return symbol(); }
  "throws"                       { return symbol(); }
  "transient"                    { return symbol(); }
  "try"                          { return symbol(); }
  "void"                         { return symbol(Token.DECLARER); }
  "volatile"                     { return symbol(); }
  "while"                        { return symbol(); }
  
  /* boolean literals */
  "true"                         { return symbol(); }
  "false"                        { return symbol(); }

  /* null literal */
  "null"                         { return symbol(); }


  /* separators */
  "("                            { return symbol(); }
  ")"                            { return symbol(); }
  "{"                            { return symbol(); }
  "}"                            { return symbol(); }
  "[]"                           { return symbol(Token.DECLARER); }
  "["                            { return symbol(); }
  "]"                            { return symbol(); }
  ";"                            { return symbol(); }
  ","                            { return symbol(Token.COMMA); }
  "."                            { return symbol(); }

  /* operators */
  "="                            { return symbol(); }
  ">"                            { return symbol(Token.GREATER); }
  "<"                            { return symbol(Token.LESS); }
  "!"                            { return symbol(); }
  "~"                            { return symbol(); }
  "?"                            { return symbol(); }
  ":"                            { return symbol(); }
  "=="                           { return symbol(); }
  "<="                           { return symbol(); }
  ">="                           { return symbol(); }
  "!="                           { return symbol(); }
  "&&"                           { return symbol(); }
  "||"                           { return symbol(); }
  "++"                           { return symbol(); }
  "--"                           { return symbol(); }
  "+"                            { return symbol(); }
  "-"                            { return symbol(); }
  "*"                            { return symbol(); }
  "/"                            { return symbol(); }
  "&"                            { return symbol(); }
  "|"                            { return symbol(); }
  "^"                            { return symbol(); }
  "%"                            { return symbol(); }
  "<<"                           { return symbol(); }
  ">>"                           { return symbol(); }
  ">>>"                          { return symbol(); }
  "+="                           { return symbol(); }
  "-="                           { return symbol(); }
  "*="                           { return symbol(); }
  "/="                           { return symbol(); }
  "&="                           { return symbol(); }
  "|="                           { return symbol(); }
  "^="                           { return symbol(); }
  "%="                           { return symbol(); }
  "<<="                          { return symbol(); }
  ">>="                          { return symbol(); }
  ">>>="                         { return symbol(); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* character literal */
  \'                             { yybegin(CHARLITERAL); }

  /* numeric literals */

  
  {DecIntegerLiteral}            { return symbol(); }
  {DecLongLiteral}               { return symbol(); }

  {HexIntegerLiteral}            { return symbol(); }
  {HexLongLiteral}               { return symbol(); }

  {OctIntegerLiteral}            { return symbol(); }
  {OctLongLiteral}               { return symbol(); }

  {FloatLiteral}                 { return symbol(); }
  {DoubleLiteral}                { return symbol(); }
  {DoubleLiteral}[dD]            { return symbol(); }

  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return symbol(Token.IDENTIFIER); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol(Token.OTHER, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8);
                        				   string.append( val ); }

  /* error cases */
  \\.                            { return symbol(); }
  {LineTerminator}               { return symbol(); }
}

<CHARLITERAL> {
  {SingleCharacter}\'            { yybegin(YYINITIAL); return symbol(); }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\t"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\n"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\f"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\r"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\\""\'                       { yybegin(YYINITIAL); return symbol();}
  "\\'"\'                        { yybegin(YYINITIAL); return symbol();}
  "\\\\"\'                       { yybegin(YYINITIAL); return symbol(); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
			                              int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
			                            return symbol(); }

  /* error cases */
  \\.                            { return symbol();  }
  {LineTerminator}               { return symbol();  }
}

/* error fallback */
[^]                              { return symbol(); }
<<EOF>>                          { return symbol(Token.EOF); }
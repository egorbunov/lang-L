package ru.spbau.mit.langl.gen;

import ru.spbau.mit.langl.lex.*;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

// ===================== LEXER ==========================
%%

%class Lexer
%public
%cup
%unicode
%line
%column

%{
    ComplexSymbolFactory symbolFactory;

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
        this(in);
        symbolFactory = sf;
    }

    private int from() {
        return yycolumn;
    }

    private int to() {
        return yycolumn + yytext().length();
    }

    private Symbol symbol(String name, int sym) {
        return symbolFactory.newSymbol(name, sym,
                                      new Location(yyline+1, yycolumn+1),
                                      new Location(yyline+1, yycolumn+yylength()));
    }

    private Symbol symbol(String name, int sym, Object val) {
        Location left = new Location(yyline+1, yycolumn+1);
        Location right= new Location(yyline+1, yycolumn+yylength());
        return symbolFactory.newSymbol(name, sym, left, right, val);
    }

  private void error(String message) {
    System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }
%}


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

%%

<YYINITIAL> {
    // keywords
    "if"                           { return symbol("IF", Sym.IF); }
    "then"                         { return symbol("THEN", Sym.THEN); }
    "else"                         { return symbol("ELSE", Sym.ELSE); }
    "read"                         { return symbol("READ", Sym.READ); }
    "write"                        { return symbol("WRITE", Sym.WRITE); }
    "do"                           { return symbol("DO", Sym.DO); }
    "skip"                         { return symbol("SKIP", Sym.SKIP); }
    "while"                        { return symbol("WHILE", Sym.WHILE); }
    "od"                           { return symbol("ENDWHILE", Sym.ENDWHILE); }
    "endif"                        { return symbol("ENDIF", Sym.ENDIF); }


    // operators
    "+"                            { return symbol("PLUS", Sym.PLUS); }
    "-"                            { return symbol("MINUS", Sym.MINUS); }
    "*"                            { return symbol("MUL", Sym.MUL); }
    "/"                            { return symbol("DIV", Sym.DIV); }
    "%"                            { return symbol("MOD", Sym.MOD); }
    "=="                           { return symbol("EQ", Sym.EQ); }
    "!="                           { return symbol("NEQ", Sym.NEQ); }
    ">"                            { return symbol("GE", Sym.GE); }
    ">="                           { return symbol("GEQ", Sym.GEQ); }
    "<"                            { return symbol("LE", Sym.LE); }
    "<="                           { return symbol("LEQ", Sym.LEQ); }
    "&&"                           { return symbol("LAND", Sym.LAND); }
    "||"                           { return symbol("LOR", Sym.LOR); }
    ":="                           { return symbol("ASSIGN", Sym.ASSIGN); }

    // special chars
    {WhiteSpace}                   { /* ignore */ }
    ";"                            { return symbol("COLON", Sym.COLON); }
    "("                            { return symbol("LPAREN", Sym.LPAREN);}
    ")"                            { return symbol("RPAREN", Sym.RPAREN);}

    // nums and ids
    {Identifier}                   { return symbol("ID", Sym.ID, yytext()); }
    {DecIntegerLiteral}            { return symbol("INT", Sym.INT, Integer.valueOf(yytext())); }
}

<<EOF>>                            { return symbolFactory.newSymbol("EOF", Sym.EOF,
                                                                    new Location(yyline+1,yycolumn+1),
                                                                    new Location(yyline+1,yycolumn+1)); }
[^]                                { throw new IllegalCharacterException(yytext(), from()); }


package ru.spbau.mit.langl.gen;

import ru.spbau.mit.langl.lex.*;
import java_cup.runtime.*;

// ===================== LEXER ==========================
%%

%class Lexer
%public
%cup
%unicode
%line
%column

%{
    private int from() {
        return yycolumn;
    }

    private int to() {
        return yycolumn + yytext().length();
    }

    private Symbol symbol(Keyword kw) {
        return new Symbol(kw.getId(), from(), to());
    }

    private Symbol symbol(Keyword kw, Object value) {
        return new Symbol(kw.getId(), from(), to(), value);
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
    "if"                           { return symbol(Keyword.IF); }
    "then"                         { return symbol(Keyword.THEN); }
    "else"                         { return symbol(Keyword.ELSE); }
    "read"                         { return symbol(Keyword.READ); }
    "write"                        { return symbol(Keyword.WRITE); }
    "do"                           { return symbol(Keyword.DO); }
    "skip"                         { return symbol(Keyword.SKIP); }

    // operators
    "+"                            { return symbol(Keyword.PLUS); }
    "-"                            { return symbol(Keyword.MINUS); }
    "*"                            { return symbol(Keyword.MUL); }
    "/"                            { return symbol(Keyword.DIV); }
    "%"                            { return symbol(Keyword.MOD); }
    "=="                           { return symbol(Keyword.EQ); }
    "!="                           { return symbol(Keyword.NEQ); }
    ">"                            { return symbol(Keyword.GE); }
    ">="                           { return symbol(Keyword.GEQ); }
    "<"                            { return symbol(Keyword.LE); }
    "<="                           { return symbol(Keyword.LEQ); }
    "&&"                           { return symbol(Keyword.LAND); }
    "||"                           { return symbol(Keyword.LOR); }
    ":="                           { return symbol(Keyword.ASSIGN); }

    // special chars
    {WhiteSpace}                   { /* ignore */ }
    ";"                            { return symbol(Keyword.COLON); }

    // nums and ids
    {Identifier}                   { return symbol(Keyword.ID, yytext()); }
    {DecIntegerLiteral}            { return symbol(Keyword.INT, Integer.valueOf(yytext())); }
}

<<EOF>>                            { return new Symbol(Keyword.EOF.getId()); }
[^]                                { throw new IllegalCharacterException(yytext(), from()); }
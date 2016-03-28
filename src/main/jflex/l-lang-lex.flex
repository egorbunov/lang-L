package ru.spbau.mit.langl.gen;

import ru.spbau.mit.langl.lex.*;

%%

%class Lexer
%public
%unicode
%line
%column
%type Token

%{
    private int from() {
        return yycolumn;
    }

    private int to() {
        return yycolumn + yytext().length();
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
    "if"                           { return new Keyword(KeywordId.IF, from(), to()); }
    "then"                         { return new Keyword(KeywordId.THEN, from(), to()); }
    "else"                         { return new Keyword(KeywordId.ELSE, from(), to()); }
    "read"                         { return new Keyword(KeywordId.READ, from(), to()); }
    "write"                        { return new Keyword(KeywordId.WRITE, from(), to()); }
    "do"                           { return new Keyword(KeywordId.DO, from(), to()); }
    "skip"                         { return new Keyword(KeywordId.SKIP, from(), to()); }

    // operators
    "+"                            { return new Operator(OperatorId.PLUS, from(), to()); }
    "-"                            { return new Operator(OperatorId.MINUS, from(), to()); }
    "*"                            { return new Operator(OperatorId.MUL, from(), to()); }
    "/"                            { return new Operator(OperatorId.DIV, from(), to()); }
    "%"                            { return new Operator(OperatorId.MOD, from(), to()); }
    "=="                           { return new Operator(OperatorId.EQ, from(), to()); }
    "!="                           { return new Operator(OperatorId.NEQ, from(), to()); }
    ">"                            { return new Operator(OperatorId.GE, from(), to()); }
    ">="                           { return new Operator(OperatorId.GEQ, from(), to()); }
    "<"                            { return new Operator(OperatorId.LE, from(), to()); }
    "<="                           { return new Operator(OperatorId.LEQ, from(), to()); }
    "&&"                           { return new Operator(OperatorId.LAND, from(), to()); }
    "||"                           { return new Operator(OperatorId.LOR, from(), to()); }
    ":="                           { return new Operator(OperatorId.ASSIGN, from(), to()); }

    // special chars
    {WhiteSpace}                   { /* ignore */ }
    ";"                            { return new Colon(from(), to()); }

    // nums and ids
    {Identifier}                   { return new Var(yytext(), from(), to()); }
    {DecIntegerLiteral}            { return new Num(Integer.valueOf(yytext()), from(), to()); }
}

[^]                                { throw new IllegalCharacterException(yytext(), from()); }
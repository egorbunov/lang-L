package ru.spbau.mit.langl.lex

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

enum class KeywordId {
    IF,
    THEN,
    ELSE,
    READ,
    WRITE,
    WHILE,
    DO,
    SKIP
}

enum class OperatorId {
    PLUS, // +
    MINUS, // -
    MUL, // *
    DIV, // /
    MOD, // %
    EQ, // ==
    NEQ, // !=
    GE, // >
    GEQ, // >=
    LE, // <
    LEQ, // <=
    LAND, // &&
    LOR,   // ||
    ASSIGN // := not sure about this
}

abstract class Token(val from: Int, val to: Int) {
    override fun toString(): String {
        return "[$from, $to)";
    }
}

class Keyword(val kw: KeywordId, from: Int, to: Int) : Token(from, to) {
    override fun toString(): String {
        return "Keyword(${kw.name}, ${super.toString()})"
    }
}

class Operator(val op: OperatorId, from: Int, to: Int) : Token(from, to) {
    override fun toString(): String {
        return "Op(${op.name}, ${super.toString()})"
    }
}

class Var(val name: String, from: Int, to: Int) : Token(from, to) {
    override fun toString(): String {
        return "Var($name, ${super.toString()})"
    }
}

class Colon(from: Int, to: Int) : Token(from, to) {
    override fun toString(): String {
        return "Colon(${super.toString()})"
    }
}

class Num(val num: Int, from: Int, to: Int) : Token(from ,to) {
    override fun toString(): String {
        return "Num($num, ${super.toString()})"
    }
}

class IllegalCharacterException(str: String, pos: Int) :
        RuntimeException("Illegal input [ $str ] at position [ $pos ] ") {
}


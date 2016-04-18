package ru.spbau.mit.langl.lex

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

enum class Keyword(val id: Int) {
    IF(0),
    THEN(1),
    ELSE(2),
    READ(3),
    WRITE(4),
    DO(5),
    SKIP(6),
    PLUS(7),
    MINUS(8),
    MUL(9),
    DIV(10),
    MOD(11),
    EQ(12),
    NEQ(13),
    GE(14),
    GEQ(15),
    LE(16),
    LEQ(17),
    LAND(18),
    LOR(19),
    ASSIGN(20),
    COLON(21),
    INT(22),
    ID(23),
    EOF(24);

    companion object {
        fun fromInt(id: Int): Keyword {
            for (kw in Keyword.values()) {
                if (kw.id == id) {
                    return kw
                }
            }
            throw RuntimeException("Bad keyword id!")
        }
    }
}

class IllegalCharacterException(str: String, pos: Int) :
        RuntimeException("Illegal input [ $str ] at position [ $pos ] ") {
}

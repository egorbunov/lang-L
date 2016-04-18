package ru.spbau.mit.langl.sym

import java_cup.runtime.Symbol
import ru.spbau.mit.langl.lex.Keyword

/**
 * Created by Egor Gorbunov on 11.04.2016.
 * email: egor-mailbox@ya.ru
 */

fun Symbol.myToString(): String {
    if (value != null) {
        return "${Keyword.fromInt(sym)}($value, $left, $right)"
    }
    return "${Keyword.fromInt(sym)}($left, $right)"
}
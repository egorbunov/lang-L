package ru.spbau.mit.langl.lex

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

class IllegalCharacterException(str: String, pos: Int) :
        RuntimeException("Illegal input [ $str ] at position [ $pos ] ") {
}

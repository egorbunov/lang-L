package ru.spbau.mit.langl

import ru.spbau.mit.langl.lex.IllegalCharacterException
import ru.spbau.mit.langl.lex.Token
import ru.spbau.mit.langl.gen.Lexer
import java.io.StringReader
import java.util.*

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

fun main(args: Array<String>) {
    var lexer = Lexer(StringReader(""))
    while (true) {
        print(">> ")
        val program = readLine() ?: break;
        lexer.yyreset(StringReader(program))

        var tokens = ArrayList<Token>()

        try {
            while (true) {
                tokens.add(lexer.yylex() ?: break)
            }
            println(tokens)
        } catch (err: IllegalCharacterException) {
           println(err.message)
        }
    }
}
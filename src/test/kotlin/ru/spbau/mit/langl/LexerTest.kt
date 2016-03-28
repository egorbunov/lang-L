package ru.spbau.mit.langl

import org.junit.Test
import ru.spbau.mit.langl.gen.Lexer
import ru.spbau.mit.langl.lex.IllegalCharacterException
import java.io.StringReader
import kotlin.test.expect

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

class LexerTest {
    @Test
    fun simpleTest() {
        val lexer = Lexer(StringReader("x := 123"));
        while (true) {
            val tok = lexer.yylex() ?: break
            println(tok)
        }
        println("------------------------------------")
    }

    @Test
    fun test2() {
        val lexer = Lexer(StringReader("read x; if y + 1 == x then write y else skip"));
        while (true) {
            val tok = lexer.yylex() ?: break
            println(tok)
        }
    }

    fun test3() {
//        val lexer = Lexer(StringReader("x = y"));
//        while (true) {
//            val tok = lexer.yylex() ?: break
//            println(tok)
//        }
    }
}



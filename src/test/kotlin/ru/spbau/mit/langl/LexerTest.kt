package ru.spbau.mit.langl

import org.junit.Test
import ru.spbau.mit.langl.gen.Lexer
import ru.spbau.mit.langl.lex.Keyword
import ru.spbau.mit.langl.sym.myToString
import java.io.StringReader

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

class LexerTest {

    fun printTokens(lexer : Lexer) {
        do {
            val tok = lexer.next_token();
            println(tok.myToString())
        } while(tok.sym != Keyword.EOF.id)
    }

    @Test fun simpleTest() {
        val lexer = Lexer(StringReader("x := 123"));
        printTokens(lexer)
        println("------------------------------------")
    }

    @Test fun test2() {
        val lexer = Lexer(StringReader("read x; if y + 1 == x then write y else skip"));
        printTokens(lexer)
        println("------------------------------------")
    }

    @Test fun test3() {
        val lexer = Lexer(StringReader("x = y"));
        printTokens(lexer)
        println("------------------------------------")
    }
}



package ru.spbau.mit.langl

import java_cup.runtime.ComplexSymbolFactory
import org.junit.Assert
import org.junit.Test
import ru.spbau.mit.langl.gen.Lexer
import ru.spbau.mit.langl.gen.Sym
import java.io.StringReader
import java.util.*

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

class LexerTest {

    fun getSymbols(lexer : Lexer): Array<Int> {
        val tokens = ArrayList<Int>()
        do {
            val sym = lexer.next_token().sym
            tokens.add(sym)
        } while(sym != Sym.EOF)
        tokens.removeAt(tokens.size - 1)
        return tokens.toArray(Array(tokens.size, {0}))
    }

    @Test fun test1() {
        val lexer = Lexer(StringReader("x := 123"), ComplexSymbolFactory())
        Assert.assertArrayEquals(
                arrayOf(Sym.ID, Sym.ASSIGN, Sym.INT),
                getSymbols(lexer));
    }

    @Test fun test2() {
        val lexer = Lexer(StringReader("read x; if y + 1 == x then write y else skip"), ComplexSymbolFactory())
        Assert.assertArrayEquals(
                arrayOf(Sym.READ, Sym.ID, Sym.COLON, Sym.IF, Sym.ID, Sym.PLUS, Sym.INT,
                        Sym.EQ, Sym.ID, Sym.THEN, Sym.WRITE, Sym.ID, Sym.ELSE, Sym.SKIP),
                getSymbols(lexer));
    }

    @Test fun test3() {
        val lexer = Lexer(StringReader("x != y"), ComplexSymbolFactory())
        Assert.assertArrayEquals(
                arrayOf(Sym.ID, Sym.NEQ, Sym.ID),
                getSymbols(lexer));
    }
}



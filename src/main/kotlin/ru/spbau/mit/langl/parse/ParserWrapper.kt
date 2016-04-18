package ru.spbau.mit.langl.parse

import java_cup.runtime.ComplexSymbolFactory
import java_cup.runtime.ScannerBuffer
import ru.spbau.mit.langl.gen.Lexer
import ru.spbau.mit.langl.gen.Parser
import java.io.Reader
import java.util.*

/**
 * Created by Egor Gorbunov on 19.04.2016.
 * email: egor-mailbox@ya.ru
 */

class ParserWrapper(val input: Reader) {
    private val csf = ComplexSymbolFactory()
    private val lexer = ScannerBuffer(Lexer(input, csf))
    private val parser = Parser(lexer, csf)
    private var result: Program? = null

    fun parse(): Program? {
        val sym = parser.parse()
        result = sym.value as Program?
        return result
    }

    fun getResult(): Program? {
        return result
    }
}

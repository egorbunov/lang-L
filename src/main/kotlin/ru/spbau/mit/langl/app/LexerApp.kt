package ru.spbau.mit.langl.app

import java_cup.runtime.ComplexSymbolFactory
import ru.spbau.mit.langl.gen.Lexer
import ru.spbau.mit.langl.gen.Sym
import ru.spbau.mit.langl.lex.IllegalCharacterException
import java.io.StringReader

/**
 * Created by Egor Gorbunov on 21.04.2016.
 * email: egor-mailbox@ya.ru
 */

class LexerApp: App {
    override fun run() {
        while (true) {
            print("lexer >> ")
            val program = readLine() ?: break;
            val lexer = Lexer(StringReader(program), ComplexSymbolFactory())
            try {
                do {
                    val tok = lexer.next_token() as ComplexSymbolFactory.ComplexSymbol
                    val sym = tok.sym

                    print("${Sym.terminalNames[sym]}(")
                    if (tok.value != null) {
                        print("\"${tok.value.toString()}\", ")
                    }
                    print("${tok.getLeft().column}, ${tok.getRight().column}); ")
                } while (sym != Sym.EOF)
            } catch (e: IllegalCharacterException) {
                println()
                print("   Lexing error: ${e.message}")
            }
            println()
        }
    }
}
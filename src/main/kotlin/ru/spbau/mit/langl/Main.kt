package ru.spbau.mit.langl

import ru.spbau.mit.langl.app.AstVisualizeApp
import ru.spbau.mit.langl.app.LexerApp
import ru.spbau.mit.langl.app.PrettyPrintApp
import ru.spbau.mit.langl.gen.Lexer
import java.io.StringReader

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

val LEXER = "lex"
val AST_VISUALIZER = "ast"
val PRETTY_PRINTER = "pp"
val OPTIMIZER = "opt"

fun help() {
    println("Formal languages course lab. project. Specify exactly one command line argument")
    println("to run desirable tool. Every tool works as interpreter: you give line, and it answers.")
    println("Here is the list of available tools (arguments):")
    println("   $LEXER")
    println("       Lexer tool...just lexer")
    println("   $AST_VISUALIZER")
    println("       Parses given line, generates and shows Abstract Syntax Tree")
    println("   $PRETTY_PRINTER")
    println("       Parses given line, prints pretty version of program")
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        help()
        return
    }

    when (args[0]) {
        LEXER -> LexerApp().run()
        AST_VISUALIZER -> AstVisualizeApp().run()
        PRETTY_PRINTER -> PrettyPrintApp().run()
        else -> help()
    }
}
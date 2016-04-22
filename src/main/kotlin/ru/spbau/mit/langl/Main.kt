package ru.spbau.mit.langl

import ru.spbau.mit.langl.app.AstVisualizeApp
import ru.spbau.mit.langl.app.LexerApp
import ru.spbau.mit.langl.app.PrettyPrintApp
import ru.spbau.mit.langl.app.SimplifierAstApp

/**
 * Created by Egor Gorbunov on 28.03.2016.
 * email: egor-mailbox@ya.ru
 */

val LEXER = "lex"
val AST_VISUALIZER = "ast"
val PRETTY_PRINTER = "pp"
val AST_OPTIMIZER = "opt"

fun help() {
    println("Hello! That is formal languages course lab. project. ")
    println("Study language L used with few additions: ")
    println("    * Unary minus operation added")
    println("    * New IF statement syntax (endif added): if E then P else P endif")
    println("    * New WHILE statement syntax (od added): while E do P od")
    println("Example programs: ")
    println("    * VAR := (1 || 1) - (-2)")
    println("    * if x <= y then write x else skip endif")
    println("    * while x <= y || y == z do read x; write y; z := 5 + 5 od")
    println("Specify exactly one command line argument to run desirable tool.")
    println("Every tool works as interpreter: you give line, and it answers.")
    println("Here is the list of available tools (arguments):")
    println("   $LEXER")
    println("       Lexer tool...just lexer")
    println("   $AST_VISUALIZER")
    println("       Parses given line, generates and shows Abstract Syntax Tree")
    println("   $PRETTY_PRINTER")
    println("       Parses given line, prints pretty version of program")
    println("   $AST_OPTIMIZER")
    println("       Optimizes given program, optimizations supported:")
    println("           * Arithmetic expressions evaluation")
    println("           * (x OP x) relations evaluated to numbers")
    println("           * Predicates with numbers operands evaluation")
    println("           * Simplification of (0 * E), (0 / E), (0 % E), (0 + E), (E - 0), etc.")
    println("           * Unreachable IF and WHILE bodies deletion")

}

fun main(args: Array<String>) {
    if (args.size != 1) {
        help()
        return
    }
    help()
    when (args[0]) {
        LEXER -> LexerApp().run()
        AST_VISUALIZER -> AstVisualizeApp().run()
        PRETTY_PRINTER -> PrettyPrintApp().run()
        AST_OPTIMIZER -> SimplifierAstApp().run()
        else -> help()
    }
}
package ru.spbau.mit.langl.app

import ru.spbau.mit.langl.parse.ParserWrapper
import ru.spbau.mit.langl.visitors.PrettyPrinter
import java.io.OutputStreamWriter
import java.io.StringReader

/**
 * Created by Egor Gorbunov on 21.04.16.
 * email: egor-mailbox@ya.ru
 */

class PrettyPrintApp: App {
    override fun run() {
        val out = OutputStreamWriter(System.out)
        while (true) {
            print("pp >> ")
            val program = readLine() ?: break;
            val parse = try {
                ParserWrapper(StringReader(program)).parse()!!
            } catch (e: Exception) {
                out.flush()
                System.err.flush()
                System.out.flush()
                continue
            }
            PrettyPrinter.print(parse, out)
            out.flush()
            println()
        }
    }
}
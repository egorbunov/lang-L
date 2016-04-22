package ru.spbau.mit.langl.app

import ru.spbau.mit.langl.parse.ParserWrapper
import ru.spbau.mit.langl.visitors.PrettyPrinter
import ru.spbau.mit.langl.visitors.Simplifier
import ru.spbau.mit.langl.visitors.VisTreeBuilder
import ru.spbau.mit.langl.visualize.AstVisVertexFillPaintTransformer
import ru.spbau.mit.langl.visualize.TreeVisApplet
import java.io.OutputStreamWriter
import java.io.StringReader
import javax.swing.JFrame

/**
 * Created by Egor Gorbunov on 22.04.2016.
 * email: egor-mailbox@ya.ru
 */

class SimplifierAstApp: App {
    override fun run() {
        val frame = JFrame()
        val content = frame.contentPane
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val out = OutputStreamWriter(System.out)

        var applet: TreeVisApplet<VisTreeBuilder.Vertex, Int>? = null

        while(true) {
            print("opt >> ")
            val program = readLine() ?: break

            val parser = ParserWrapper(StringReader(program))
            val res = try {
                parser.parse()!!
            } catch (e: Exception) {
                println("ERROR: Can't parse given program...")
                println(e.toString())
                continue
            }

            val treeBuilder = VisTreeBuilder()
            val newP = res.accept(Simplifier())

            if (newP != null) {
                newP.accept(treeBuilder)
                PrettyPrinter.print(newP, out)
            }

            out.flush()

            val tree = treeBuilder.tree

            frame.isVisible = false

            if (applet != null) {
                frame.remove(applet)
            }

            applet = TreeVisApplet(tree, AstVisVertexFillPaintTransformer())
            content.add(applet)
            frame.pack()
            frame.isVisible = true
        }
    }
}
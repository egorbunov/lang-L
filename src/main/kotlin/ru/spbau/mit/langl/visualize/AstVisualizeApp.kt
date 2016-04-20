package ru.spbau.mit.langl.visualize

import ru.spbau.mit.langl.parse.ParserWrapper
import ru.spbau.mit.langl.visitors.VisTreeBuilder
import java.io.StringReader
import javax.swing.JFrame

/**
 * Created by Egor Gorbunov on 21.04.2016.
 * email: egor-mailbox@ya.ru
 */

fun main(args: Array<String>) {
    val frame = JFrame()
    val content = frame.contentPane
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    var applet: TreeVisApplet<VisTreeBuilder.Vertex, Int>? = null

    while(true) {
        print("program >> ")
        val program = readLine() ?: break

        val parser = ParserWrapper(StringReader(program))
        val res = try {
            parser.parse()!!
        } catch (e: Exception) {
            println("ERROR: Can't parse given program...")
            continue
        }

        val treeBuilder = VisTreeBuilder()
        res.accept(treeBuilder)
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

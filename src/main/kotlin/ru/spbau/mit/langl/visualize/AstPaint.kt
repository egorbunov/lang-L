package ru.spbau.mit.langl.visualize

import com.google.common.base.Function
import ru.spbau.mit.langl.visitors.VisTreeBuilder
import java.awt.Color
import java.awt.Paint

class AstVisVertexFillPaintTransformer : Function<VisTreeBuilder.Vertex, Paint> {
    override fun apply(v: VisTreeBuilder.Vertex?): Paint? {
        if (v!!.label == "ROOT") {
            return Color.GRAY
        }

        if (v.isTreminal == true) {
            return Color.GREEN
        } else {
            return Color.RED
        }
    }
}


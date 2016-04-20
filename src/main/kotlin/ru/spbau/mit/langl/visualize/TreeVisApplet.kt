package ru.spbau.mit.langl.visualize

import com.google.common.base.Function
import com.google.common.base.Functions
import edu.uci.ics.jung.algorithms.layout.TreeLayout
import edu.uci.ics.jung.graph.Tree
import edu.uci.ics.jung.visualization.GraphZoomScrollPane
import edu.uci.ics.jung.visualization.VisualizationViewer
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse
import edu.uci.ics.jung.visualization.control.ModalGraphMouse
import edu.uci.ics.jung.visualization.decorators.EdgeShape
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller
import edu.uci.ics.jung.visualization.renderers.Renderer
import java.awt.*
import javax.swing.JApplet
import javax.swing.JPanel

class TreeVisApplet<V, E>(var graph: Tree<V, E>,
                          fillPaintTransformer: Function<V, Paint>? = Function<V, Paint> { Color.RED },
                          labelTransformer: Function<V, String> = Function<V, String> { it.toString() })
: JApplet() {
    private var vv: VisualizationViewer<V, E>
    private var treeLayout: TreeLayout<V, E>

    init {
        treeLayout = TreeLayout(graph)

        vv = VisualizationViewer(treeLayout, Dimension(400, 400))

        vv.background = Color.white
        vv.renderContext.edgeShapeTransformer = EdgeShape.line(graph)
        vv.renderContext.vertexLabelTransformer = labelTransformer
        vv.renderContext.vertexFillPaintTransformer = fillPaintTransformer

        // centering label position
        vv.renderer.vertexLabelRenderer.position = Renderer.VertexLabel.Position.CNTR

        /**
         * Shape resized to label size
         */
        vv.renderContext.vertexShapeTransformer = Function<V, java.awt.Shape> { v ->
            val widAdd = 10
            val heightAdd = 5
            val label = labelTransformer.apply(v)
            val fm = vv.getFontMetrics(vv.font)
            val width = fm.stringWidth(label) + widAdd
            val height = fm.height + heightAdd

            Rectangle(-width / 2, -height / 2, width, height)
        }

        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(ToStringLabeller())
        vv.renderContext.arrowFillPaintTransformer = Functions.constant<Paint>(Color.lightGray)

        val content = contentPane
        val panel = GraphZoomScrollPane(vv)
        content.add(panel)
        val graphMouse = DefaultModalGraphMouse<String, Int>()
        vv.graphMouse = graphMouse
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING)
        val controls = JPanel()
        content.add(controls, BorderLayout.SOUTH)
    }
}


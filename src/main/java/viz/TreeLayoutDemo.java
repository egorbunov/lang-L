package viz;

import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.*;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;

/**
 * Demonsrates TreeLayout and RadialTreeLayout.
 * @author Tom Nelson
 *
 */
@SuppressWarnings("serial")
public class TreeLayoutDemo extends JApplet {
    /**
     * the graph
     */
    Forest<String,Integer> graph;

    Supplier<DirectedGraph<String,Integer>> graphFactory =
            new Supplier<DirectedGraph<String,Integer>>() {

                public DirectedGraph<String, Integer> get() {
                    return new DirectedSparseMultigraph<String,Integer>();
                }
            };

    Supplier<Tree<String,Integer>> treeFactory =
            new Supplier<Tree<String,Integer>> () {

                public Tree<String, Integer> get() {
                    return new DelegateTree<String,Integer>(graphFactory);
                }
            };

    Supplier<Integer> edgeFactory = new Supplier<Integer>() {
        int i=0;
        public Integer get() {
            return i++;
        }};

    Supplier<String> vertexFactory = new Supplier<String>() {
        int i=0;
        public String get() {
            return "V"+i++;
        }};

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String,Integer> vv;

    TreeLayout<String,Integer> treeLayout;

    RadialTreeLayout<String,Integer> radialLayout;

    public TreeLayoutDemo() {
        // create a simple graph for the demo
        graph = new DelegateForest<String,Integer>();

        createTree();

        treeLayout = new TreeLayout<String,Integer>(graph);
        radialLayout = new RadialTreeLayout<String,Integer>(graph);

        radialLayout.setSize(new Dimension(600,600));
        vv =  new VisualizationViewer<String,Integer>(treeLayout, new Dimension(600,600));
        vv.setBackground(Color.white);
        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);


        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller());
        vv.getRenderContext().setArrowFillPaintTransformer(Functions.<Paint>constant(Color.lightGray));

        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);

        final DefaultModalGraphMouse<String, Integer> graphMouse
                = new DefaultModalGraphMouse<String, Integer>();

        vv.setGraphMouse(graphMouse);

        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        JPanel controls = new JPanel();

        content.add(controls, BorderLayout.SOUTH);
    }

    /**
     *
     */
    private void createTree() {
        graph.addVertex("V0");
        graph.addEdge(edgeFactory.get(), "V0", "V1");
        graph.addEdge(edgeFactory.get(), "V0", "V2");
        graph.addEdge(edgeFactory.get(), "V1", "V4");
        graph.addEdge(edgeFactory.get(), "V2", "V3");
        graph.addEdge(edgeFactory.get(), "V2", "V5");
        graph.addEdge(edgeFactory.get(), "V4", "V6");
        graph.addEdge(edgeFactory.get(), "V4", "V7");
        graph.addEdge(edgeFactory.get(), "V3", "V8");
        graph.addEdge(edgeFactory.get(), "V6", "V9");
        graph.addEdge(edgeFactory.get(), "V4", "V10");

        graph.addVertex("A0");
        graph.addEdge(edgeFactory.get(), "A0", "A1");
        graph.addEdge(edgeFactory.get(), "A0", "A2");
        graph.addEdge(edgeFactory.get(), "A0", "A3");

        graph.addVertex("B0");
        graph.addEdge(edgeFactory.get(), "B0", "B1");
        graph.addEdge(edgeFactory.get(), "B0", "B2");
        graph.addEdge(edgeFactory.get(), "B1", "B4");
        graph.addEdge(edgeFactory.get(), "B2", "B3");
        graph.addEdge(edgeFactory.get(), "B2", "B5");
        graph.addEdge(edgeFactory.get(), "B4", "B6");
        graph.addEdge(edgeFactory.get(), "B4", "B7");
        graph.addEdge(edgeFactory.get(), "B3", "B8");
        graph.addEdge(edgeFactory.get(), "B6", "B9");

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content.add(new TreeLayoutDemo());
        frame.pack();
        frame.setVisible(true);
    }
}
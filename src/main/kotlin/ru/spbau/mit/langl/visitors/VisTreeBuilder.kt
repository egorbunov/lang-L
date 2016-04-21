package ru.spbau.mit.langl.visitors

import com.google.common.base.Supplier
import edu.uci.ics.jung.graph.DelegateTree
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph
import ru.spbau.mit.langl.parse.*

/**
 * Created by Egor Gorbunov on 20.04.2016.
 * email: egor-mailbox@ya.ru
 */

class VisTreeBuilder : AstTreeVisitor {
    /**
     * Tree, that is built by visitor
     */
    val tree = DelegateTree<Vertex, Int>(DirectedOrderedSparseMultigraph<Vertex, Int>())

    private val root = Vertex(false, "ROOT")

    /**
     * Last built vertex
     */
    private var parent: Vertex = root

    init {
        tree.addVertex(root)
    }

    private val edgeFactory: Supplier<Int> = object : Supplier<Int> {
        internal var i = 0
        override fun get(): Int {
            return i++
        }
    }

    class Vertex(val isTreminal: Boolean, val label: String) {
        override fun toString(): String {
            return label
        }
    }

    override fun visit(node: BinaryOpExpr) {
        val nodeLabel = "BINARY_OP_EXPR"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        node.lhs.accept(this)


        var opLabel = ""
        if (node.op is BinaryArithmeticOp) {
            opLabel = "BIN_ARITHM_OP"
        } else if (node.op is BinaryPredicateOp) {
            opLabel = "BIN_PRED_OP"
        } else if (node.op is RelationOp) {
            opLabel = "RELATION_OP"
        }

        val opNode = Vertex(false, opLabel)
        tree.addEdge(edgeFactory.get(), parent, opNode)
        tree.addEdge(edgeFactory.get(), opNode, Vertex(true, node.op.str()))

        node.rhs.accept(this)
        parent = oldParent
    }

    override fun visit(node: NumberNode) {
        val newNode = Vertex(false, "NUM")
        tree.addEdge(edgeFactory.get(), parent, newNode)
        tree.addEdge(edgeFactory.get(), newNode, Vertex(true, node.value.toString()))
    }

    override fun visit(node: IdNode) {
        val newNode = Vertex(false, "ID")
        tree.addEdge(edgeFactory.get(), parent, newNode)
        tree.addEdge(edgeFactory.get(), newNode, Vertex(true, node.value))
    }

    override fun visit(node: UnaryFunStatement) {
        val nodeLabel = "UNARY_FUN_EXPR"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, node.func.name))
        node.operand.accept(this)

        parent = oldParent
    }

    override fun visit(node: IfStatement) {
        val nodeLabel = "IF_ST"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, "If"))
        node.cond.accept(this)
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, "Then"))
        node.ifTrue.accept(this)
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, "Else"))
        node.ifFalse.accept(this)

        parent = oldParent
    }

    override fun visit(node: WhileStatement) {
        val nodeLabel = "WHILE_ST"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, "While"))
        node.cond.accept(this)
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, "Do"))
        node.doStmnt.accept(this)

        parent = oldParent
    }

    override fun visit(node: AssignStatement) {
        val nodeLabel = "ASSIGN_ST"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        node.id.accept(this)
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, ":="))
        node.expr.accept(this)

        parent = oldParent
    }

    override fun visit(node: CommandStatement) {
        val nodeLabel = "COMMAND_ST"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        tree.addEdge(edgeFactory.get(), parent, Vertex(true, node.cmd.name))

        parent = oldParent
    }

    override fun visit(node: Program) {
        val nodeLabel = "PROGRAM"

        val oldParent = parent
        val newNode = Vertex(false, nodeLabel)
        tree.addEdge(edgeFactory.get(), oldParent, newNode)

        parent = newNode
        for (st in node.statements) {
            st.accept(this)
            tree.addEdge(edgeFactory.get(), parent, Vertex(true, ";"))
        }

        parent = oldParent
    }
}
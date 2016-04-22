package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*
import java.io.OutputStreamWriter

/**
 * Created by Egor Gorbunov on 21.04.16.
 * email: egor-mailbox@ya.ru
 */

class PrettyPrinter() : AstTreeVisitor<Unit> {
    private val sb = StringBuilder()
    private var tabStr = ""
    private var parent: AstNode? = null

    override fun visit(node: Program) {
        val sz = node.statements.size
        node.statements.forEachIndexed { i, st ->
            sb.append(tabStr)
            st.accept(this)
            if (sz > 1 && i < sz - 1 && st !is IfStatement && st !is WhileStatement) {
                sb.append(";")
            }
            sb.append("\n")
        }
    }

    private fun doesLhsNeedParen(root: BinaryOpExpr, lhs: Expression): Boolean {
        if (lhs !is BinaryOpExpr) {
            return false
        }

        if (root.op is BinaryArithmeticOp && lhs.op !is BinaryArithmeticOp
                || root.op is BinaryPredicateOp && lhs.op !is BinaryPredicateOp
                || root.op is RelationOp
                || lhs.op is RelationOp) {
            return true
        }

        if (lhs.op in setOf(BinaryArithmeticOp.MINUS, BinaryArithmeticOp.PLUS)
                && root.op in setOf(
                BinaryArithmeticOp.DIV, BinaryArithmeticOp.MUL,
                BinaryArithmeticOp.MOD)) {
            return true
        }

        if (lhs.op == BinaryPredicateOp.LOR && root.op == BinaryPredicateOp.LAND) {
            return true
        }

        return false
    }

    private fun doesRhsNeedParen(root: BinaryOpExpr, rhs: Expression): Boolean {
        if (rhs !is BinaryOpExpr) {
            return false
        }
        return doesLhsNeedParen(root, rhs)
                || (rhs.op == BinaryArithmeticOp.PLUS && root.op == BinaryArithmeticOp.MINUS)
    }

    override fun visit(node: BinaryOpExpr) {
        var needParen = doesLhsNeedParen(node, node.lhs)

        if (needParen) {
            sb.append("(")
        }
        node.lhs.accept(this)
        if (needParen) {
            sb.append(")")
        }

        sb.append(" ${node.op.str()} ")

        needParen = doesRhsNeedParen(node, node.rhs)

        if (needParen) {
            sb.append("(")
        }

        node.rhs.accept(this)

        if (needParen) {
            sb.append(")")
        }
    }

    override fun visit(node: NumberNode) {
        sb.append(node.value)
    }

    override fun visit(node: IdNode) {
        sb.append(node.value)
    }

    override fun visit(node: UnaryFunStatement) {
        sb.append(node.func.str)
        sb.append(" ")
        node.operand.accept(this)
        sb.append("\n")
    }

    override fun visit(node: IfStatement) {
        val offset = tabStr

        sb.append("if ")
        node.cond.accept(this)
        sb.append(" then\n")
        tabStr = offset + "    "
        node.ifTrue.accept(this)
        sb.append("${offset}else\n")

        tabStr = offset + "    "
        node.ifFalse.accept(this)

        sb.append("${offset}endif")

        tabStr = offset
    }

    override fun visit(node: WhileStatement) {
        val offset = tabStr
        sb.append("while ")
        node.cond.accept(this)
        sb.append(" do\n")
        tabStr = offset + "    "
        node.doStmnt.accept(this)
        sb.append("${offset}od")

        tabStr = offset
    }

    override fun visit(node: AssignStatement) {
        node.id.accept(this)
        sb.append(" := ")
        node.expr.accept(this)
    }

    override fun visit(node: CommandStatement) {
        sb.append("${node.cmd.str}")
    }

    override fun visit(node: UnaryOpExpr) {
        sb.append(node.op.str())
        val needParen = node.rhs is BinaryOpExpr || node.rhs is UnaryOpExpr
        if (needParen) sb.append("(")
        node.rhs.accept(this)
        if (needParen) sb.append(")")
    }


    companion object {
        fun print(tree: AstNode, out: OutputStreamWriter) {
            val pp = PrettyPrinter()
            tree.accept(pp)
            out.write(pp.sb.toString())
        }
    }
}
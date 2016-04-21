package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*
import java.util.*

/**
 * Created by Egor Gorbunov on 21.04.16.
 * email: egor-mailbox@ya.ru
 */

class AstComparator : AstTreeVisitor, Comparator<AstNode> {

    private var toCompareWith: AstNode? = null
    private var curNodeToComp: AstNode? = null
    private var result = 0

    override fun visit(node: Program) {
        if (curNodeToComp !is Program) {
            result = -1
            return
        }
        val cur = curNodeToComp as Program


        if (cur.statements.size != node.statements.size) {
            result = -1
            return
        }
        val sz = cur.statements.size - 1
        for (i in 0..sz) {
            curNodeToComp = cur.statements[i]
            node.statements[i].accept(this)
            if (result != 0) {
                return
            }
        }
    }

    override fun visit(node: BinaryArithmeticExpr) {
        if (curNodeToComp !is BinaryArithmeticExpr) {
            result = -1
            return
        }
        val cur = curNodeToComp as BinaryArithmeticExpr

        if (cur.op != node.op) {
            result = -1
            return
        }

        curNodeToComp = cur.lhs
        node.lhs.accept(this)
        if (result != 0) {
            return
        }
        curNodeToComp = cur.rhs
        node.lhs.accept(this)
    }

    override fun visit(node: BinaryPredicateExpr) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: RelationExpr) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: NumberNode) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: IdNode) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: UnaryFunStatement) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: IfStatement) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: WhileStatement) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: AssignStatement) {
        throw UnsupportedOperationException()
    }

    override fun visit(node: CommandStatement) {
        throw UnsupportedOperationException()
    }

    override fun compare(o1: AstNode?, o2: AstNode?): Int {
        toCompareWith = o1!!
        o2!!.accept(this)
        return 0
    }
}

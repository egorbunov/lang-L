package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*
import java.util.*

/**
 * Created by Egor Gorbunov on 21.04.16.
 * email: egor-mailbox@ya.ru
 */

class AstComparator : AstTreeVisitor, Comparator<AstNode> {

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

    override fun visit(node: BinaryOpExpr) {
        if (curNodeToComp !is BinaryOpExpr) {
            result = -1
            return
        }
        val cur = curNodeToComp as BinaryOpExpr

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
        node.rhs.accept(this)
    }

    override fun visit(node: NumberNode) {
        if (curNodeToComp !is NumberNode || (curNodeToComp as NumberNode).value != node.value) {
            result = -1
        }
    }

    override fun visit(node: IdNode) {
        if (curNodeToComp !is IdNode || (curNodeToComp as IdNode).value != node.value) {
            result = -1
        }
    }

    override fun visit(node: UnaryFunStatement) {
        if (curNodeToComp !is UnaryFunStatement) {
            result = -1
            return
        }
        val cur = curNodeToComp as UnaryFunStatement

        if (cur.func != node.func) {
            result = -1
            return
        }
        curNodeToComp = cur.operand
        node.operand.accept(this)
    }

    override fun visit(node: IfStatement) {
        if (curNodeToComp !is IfStatement) {
            result = -1
            return
        }
        val cur = curNodeToComp as IfStatement

        curNodeToComp = cur.cond
        node.cond.accept(this)
        if (result != 0) return
        curNodeToComp = cur.ifTrue
        node.ifTrue.accept(this)
        if (result != 0) return
        curNodeToComp = cur.ifFalse
        node.ifFalse.accept(this)
    }

    override fun visit(node: WhileStatement) {
        if (curNodeToComp !is WhileStatement) {
            result = -1
            return
        }
        val cur = curNodeToComp as WhileStatement

        curNodeToComp = cur.cond
        node.cond.accept(this)
        if (result != 0) return
        curNodeToComp = cur.doStmnt
        node.doStmnt.accept(this)
    }

    override fun visit(node: AssignStatement) {
        if (curNodeToComp !is AssignStatement) {
            result = -1
            return
        }
        val cur = curNodeToComp as AssignStatement

        curNodeToComp = cur.id
        node.id.accept(this)
        if (result != 0) return
        curNodeToComp = cur.expr
        node.expr.accept(this)
    }

    override fun visit(node: CommandStatement) {
        if (curNodeToComp !is CommandStatement || (curNodeToComp as CommandStatement).cmd != node.cmd) {
            result = -1
        }
    }

    override fun compare(o1: AstNode?, o2: AstNode?): Int {
        if (o1!! === o2!!) {
            return 0
        }
        curNodeToComp = o1!!
        o2!!.accept(this)
        return result
    }
}

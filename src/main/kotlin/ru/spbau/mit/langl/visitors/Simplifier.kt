package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*

/**
 * Created by Egor Gorbunov on 22.04.16.
 * email: egor-mailbox@ya.ru
 */

class Simplifier : AstTreeVisitor<AstNode> {
    override fun visit(node: Program): AstNode {
        return node
    }

    override fun visit(node: BinaryOpExpr): AstNode {
        val sLhs = node.lhs.accept(this)
        val sRhs = node.rhs.accept(this)

        if (sLhs is NumberNode && sRhs is NumberNode) {
            return eval(sLhs, sRhs, node.op)
        }

        return node
    }

    override fun visit(node: NumberNode): AstNode {
        return node
    }

    override fun visit(node: IdNode): AstNode {
        return node
    }

    override fun visit(node: UnaryFunStatement): AstNode {
        return node
    }

    override fun visit(node: IfStatement): AstNode {
        return node
    }

    override fun visit(node: WhileStatement): AstNode {
        return node
    }

    override fun visit(node: AssignStatement): AstNode {
        return node
    }

    override fun visit(node: CommandStatement): AstNode {
        return node
    }

    private fun eval(lhs: NumberNode, rhs: NumberNode, op: BinaryOp): NumberNode {
        when (op) {
            BinaryArithmeticOp.PLUS -> return NumberNode(lhs.value + rhs.value)
            BinaryArithmeticOp.MINUS -> return NumberNode(lhs.value - rhs.value)
            BinaryArithmeticOp.MUL -> return NumberNode(lhs.value * rhs.value)
            BinaryArithmeticOp.DIV -> return NumberNode(lhs.value / rhs.value)
            BinaryArithmeticOp.MOD -> return NumberNode(lhs.value % rhs.value)

            BinaryPredicateOp.LAND -> return NumberNode(
                    if (lhs.value != 0 && rhs.value != 0) 1 else 0
            )
            BinaryPredicateOp.LOR -> return NumberNode(
                    if (lhs.value != 0 || rhs.value != 0) 1 else 0
            )

            RelationOp.EQ -> return NumberNode(if (lhs.value == rhs.value) 1 else 0)
            RelationOp.NEQ -> return NumberNode(if (lhs.value != rhs.value) 1 else 0)
            RelationOp.GE -> return NumberNode(if (lhs.value > rhs.value) 1 else 0)
            RelationOp.GEQ -> return NumberNode(if (lhs.value >= rhs.value) 1 else 0)
            RelationOp.LE -> return NumberNode(if (lhs.value < rhs.value) 1 else 0)
            RelationOp.LEQ -> return NumberNode(if (lhs.value <= rhs.value) 1 else 0)

            else -> throw IllegalStateException("Unknown binary operator: [ ${op} ]")
        }
    }

}

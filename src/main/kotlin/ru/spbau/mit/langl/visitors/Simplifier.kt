package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*

/**
 * Created by Egor Gorbunov on 22.04.16.
 * email: egor-mailbox@ya.ru
 */

class Simplifier : AstTreeVisitor<AstNode?> {
    override fun visit(node: Program): AstNode {
        val newP = Program()
        for (st in node.statements) {
            val newSt = st.accept(this) as Statement?

            if (newSt != null) {
                if (newSt is Program) {
                    newSt.statements.forEach { newP.add(it) }
                } else {
                    newP.add(newSt)
                }
            }
        }
        return newP
    }

    override fun visit(node: BinaryOpExpr): AstNode {
        node.lhs = node.lhs.accept(this) as Expression
        node.rhs = node.rhs.accept(this) as Expression

        // Handle arithmetic and simple stuff like multiply by zero, etc.

        if (node.lhs is NumberNode && node.rhs is NumberNode) {
            return eval(node.lhs as NumberNode, node.rhs as NumberNode, node.op)
        }

        if (node.lhs is NumberNode) {
            val value = (node.lhs as NumberNode).value
            if (value == 0 && node.op in setOf(BinaryArithmeticOp.MUL, BinaryArithmeticOp.DIV,
                    BinaryArithmeticOp.MOD)) {
                return node.lhs
            }

            if (value == 1 && node.op == BinaryArithmeticOp.MUL
                    || value == 0 && node.op == BinaryArithmeticOp.PLUS) {
                return node.rhs
            }
        }

        if (node.rhs is NumberNode) {
            val value = (node.rhs as NumberNode).value
            if (value == 0 && node.op == BinaryArithmeticOp.MUL) {
                return node.rhs
            }
            if (value == 1 && node.op == BinaryArithmeticOp.MUL
                    || value == 0 && node.op in setOf(BinaryArithmeticOp.PLUS, BinaryArithmeticOp.MINUS)) {
                return node.lhs
            }
        }

        // Handle relations and arithmetic with same ID nodes
        if (node.lhs is IdNode && node.rhs is IdNode
                && (node.lhs as IdNode).value == (node.rhs as IdNode).value) {
            if (node.op in setOf(RelationOp.EQ, RelationOp.GEQ, RelationOp.LEQ)) {
                return NumberNode(1)
            } else if (node.op in setOf(RelationOp.LE, RelationOp.GE, RelationOp.NEQ)
                    || node.op == BinaryArithmeticOp.MINUS) {
                return NumberNode(0)
            }
            if (node.op == BinaryArithmeticOp.PLUS) {
                node.lhs = NumberNode(2)
                node.op = BinaryArithmeticOp.MUL
            }
        }

        return node
    }

    override fun visit(node: UnaryOpExpr): AstNode {
        node.rhs = node.rhs.accept(this) as Expression

        if (node.rhs is NumberNode && node.op == UnaryArithmeticOp.MINUS) {
            return NumberNode(-(node.rhs as NumberNode).value)
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
        node.operand = node.operand.accept(this) as Expression
        return node
    }

    override fun visit(node: IfStatement): AstNode {
        node.cond = node.cond.accept(this) as Expression
        node.ifFalse = node.ifFalse.accept(this) as Program
        node.ifTrue = node.ifTrue.accept(this) as Program

        if (node.cond is NumberNode) {
            val value = (node.cond as NumberNode).value
            if (value != 0) {
                return node.ifTrue
            } else {
                return node.ifFalse
            }
        } else

        return node
    }

    override fun visit(node: WhileStatement): AstNode? {
        node.cond = node.cond.accept(this) as Expression

        if (node.cond is NumberNode && (node.cond as NumberNode).value == 0) {
            return null
        }

        node.doStmnt = node.doStmnt.accept(this) as Program
        return node
    }

    override fun visit(node: AssignStatement): AstNode {
        node.expr = node.expr.accept(this) as Expression
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

            else -> throw IllegalStateException("Unknown binary operator: [ $op ]")
        }
    }

}

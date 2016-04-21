package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*

/**
 * Created by Egor Gorbunov on 20.04.2016.
 * email: egor-mailbox@ya.ru
 */
interface AstTreeVisitor {
    fun visit(node: Program)
    fun visit(node: BinaryOpExpr)
    fun visit(node: NumberNode)
    fun visit(node: IdNode)
    fun visit(node: UnaryFunStatement)
    fun visit(node: IfStatement)
    fun visit(node: WhileStatement)
    fun visit(node: AssignStatement)
    fun visit(node: CommandStatement)
}
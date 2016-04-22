package ru.spbau.mit.langl.visitors

import ru.spbau.mit.langl.parse.*

/**
 * Created by Egor Gorbunov on 20.04.2016.
 * email: egor-mailbox@ya.ru
 */
interface AstTreeVisitor<T> {
    fun visit(node: Program): T
    fun visit(node: BinaryOpExpr): T
    fun visit(node: NumberNode): T
    fun visit(node: IdNode): T
    fun visit(node: UnaryFunStatement): T
    fun visit(node: IfStatement): T
    fun visit(node: WhileStatement): T
    fun visit(node: AssignStatement): T
    fun visit(node: CommandStatement): T
}
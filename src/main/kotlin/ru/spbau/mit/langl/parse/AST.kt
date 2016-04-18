package ru.spbau.mit.langl.parse

import java.util.*

/**
 * Created by Egor Gorbunov on 18.04.2016.
 * email: egor-mailbox@ya.ru
 */

enum class ArithmeticOp {
    PLUS, MINUS, MUL, DIV, MOD
}

enum class RelationOp {
    EQ, NEQ, GE, GEQ, LE, LEQ
}

enum class PredicateOp {
    LAND, LOR
}

enum class UnaryFun {
    WRITE, READ
}

enum class Command {
    SKIP
}

class Program {
    val statements = ArrayList<Statement>()

    fun add(st: Statement) {
        statements.add(st)
    }
}

abstract class Expression {
}

class BinaryArithmeticExpr(val op: ArithmeticOp, val lhs: Expression, val rhs: Expression): Expression() {
}

class BinaryPredicateExpr(val op: PredicateOp, val lhs: Expression, val rhs: Expression): Expression() {
}

class RelationExpr(val op: RelationOp, val lhs: Expression, val rhs: Expression): Expression() {
}

class NumberNode(val value: Int): Expression() {
}

class IdNode(val value: String): Expression() {
}

abstract class Statement {
}

class UnaryFunStatement(val func: UnaryFun, val operand: Expression): Statement() {
}

class IfStatement(val cond: Expression, val ifTrue: Statement, val ifFalse: Statement): Statement() {
}

class WhileStatement(val cond: Expression, val doStmnt: Statement): Statement() {
}

class AssignStatement(val id: IdNode, val expr: Expression): Statement() {
}

class CommandStatement(val cmd: Command): Statement() {
}

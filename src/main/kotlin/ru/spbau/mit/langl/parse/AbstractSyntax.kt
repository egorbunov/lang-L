package ru.spbau.mit.langl.parse

import ru.spbau.mit.langl.visitors.AstTreeVisitor
import java.util.*

/**
 * Created by Egor Gorbunov on 18.04.2016.
 * email: egor-mailbox@ya.ru
 */

enum class ArithmeticOp(val str: String) {
    PLUS("+"), MINUS("-"), MUL("*"), DIV("/"), MOD("%")
}

enum class RelationOp(val str: String) {
    EQ("=="), NEQ("!="), GE(">"), GEQ(">="), LE("<"), LEQ("<=")
}

enum class PredicateOp(val str: String) {
    LAND("&&"), LOR("||")
}

enum class UnaryFun {
    WRITE, READ
}

enum class Command {
    SKIP
}

abstract class Expression {
    abstract fun accept(visitor: AstTreeVisitor)
}

class BinaryArithmeticExpr(val op: ArithmeticOp, val lhs: Expression, val rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class BinaryPredicateExpr(val op: PredicateOp, val lhs: Expression, val rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class RelationExpr(val op: RelationOp, val lhs: Expression, val rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class NumberNode(val value: Int): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class IdNode(val value: String): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

abstract class Statement {
    abstract fun accept(visitor: AstTreeVisitor)
}

class UnaryFunStatement(val func: UnaryFun, val operand: Expression): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class IfStatement(val cond: Expression, val ifTrue: Statement, val ifFalse: Statement): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class WhileStatement(val cond: Expression, val doStmnt: Statement): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class AssignStatement(val id: IdNode, val expr: Expression): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class CommandStatement(val cmd: Command): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

/**
 * Statement may be combined from many statements...
 */
class Program: Statement() {
    val statements = ArrayList<Statement>()

    fun add(st: Statement) {
        statements.add(st)
    }

    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}
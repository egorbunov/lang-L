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

abstract class AstNode {
    abstract fun accept(visitor: AstTreeVisitor)
}

abstract class Expression : AstNode() {
}

class BinaryArithmeticExpr(var op: ArithmeticOp, var lhs: Expression, var rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class BinaryPredicateExpr(var op: PredicateOp, var lhs: Expression, var rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class RelationExpr(var op: RelationOp, var lhs: Expression, var rhs: Expression): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class NumberNode(var value: Int): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class IdNode(var value: String): Expression() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

abstract class Statement : AstNode() {
}

class UnaryFunStatement(var func: UnaryFun, var operand: Expression): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class IfStatement(var cond: Expression, var ifTrue: Statement, var ifFalse: Statement): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class WhileStatement(var cond: Expression, var doStmnt: Statement): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class AssignStatement(var id: IdNode, var expr: Expression): Statement() {
    override fun accept(visitor: AstTreeVisitor) {
        visitor.visit(this)
    }
}

class CommandStatement(var cmd: Command): Statement() {
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
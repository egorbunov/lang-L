package ru.spbau.mit.langl.parse

import ru.spbau.mit.langl.visitors.AstTreeVisitor
import java.util.*

/**
 * Created by Egor Gorbunov on 18.04.2016.
 * email: egor-mailbox@ya.ru
 */

interface BinaryOp {
    fun str(): String
}

enum class BinaryArithmeticOp(): BinaryOp {
    PLUS {
        override fun str() = "+"
    },
    MINUS {
        override fun str() = "-"
    },
    MUL {
        override fun str() = "*"
    },
    DIV {
        override fun str() = "/"
    },
    MOD {
        override fun str() = "%"
    }
}

enum class RelationOp(): BinaryOp {
    EQ {
        override fun str() = "=="
    },
    NEQ {
        override fun str() = "!="
    },
    GE {
        override fun str() = ">"
    },
    GEQ {
        override fun str() = ">="
    },
    LE {
        override fun str() = "<"
    },
    LEQ {
        override fun str() = "<="
    }
}

enum class BinaryPredicateOp(): BinaryOp {
    LAND {
        override fun str() = "&&"
    },
    LOR {
        override fun str() = "||"
    }
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

class BinaryOpExpr(var op: BinaryOp, var lhs: Expression, var rhs: Expression): Expression() {
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
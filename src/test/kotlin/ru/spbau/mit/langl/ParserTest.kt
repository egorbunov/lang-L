package ru.spbau.mit.langl

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.spbau.mit.langl.parse.*
import ru.spbau.mit.langl.visitors.AstComparator
import java.io.StringReader
import java.util.*

/**
 * Created by Egor Gorbunov on 18.04.2016.
 * email: egor-mailbox@ya.ru
 */

@RunWith(Parameterized::class)
class ParserTest(val program: String, val expectedAst: Program) {
    val parser = ParserWrapper(StringReader(program))

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun getTestData(): Collection<Any> {
            return Arrays.asList(*arrayOf(
                    arrayOf(
                            "x := 100",
                            with(Program()) {
                                add(
                                        AssignStatement(IdNode("x"), NumberNode(100))
                                )
                                this
                            }
                    )),
                    arrayOf(
                            "if 1 == 1 then write x else skip endif",
                            with(Program()) {
                                add(
                                        IfStatement(
                                                BinaryOpExpr(
                                                        RelationOp.EQ,
                                                        NumberNode(1),
                                                        NumberNode(1)
                                                ),
                                                Program(UnaryFunStatement(UnaryFun.WRITE, IdNode("x"))),
                                                Program(CommandStatement(Command.SKIP))
                                        )
                                )
                                this
                            }
                    ),
                    arrayOf(
                            "skip; skip",
                            with(Program()) {
                                add(CommandStatement(Command.SKIP))
                                add(CommandStatement(Command.SKIP))
                                this
                            }
                    ),
                    arrayOf(
                            "while (x == y) && (x <= z) do skip od",
                            with(Program()) {
                                add(WhileStatement(
                                        BinaryOpExpr(
                                                BinaryPredicateOp.LAND,
                                                BinaryOpExpr(
                                                        RelationOp.EQ,
                                                        IdNode("x"),
                                                        IdNode("y")
                                                ),
                                                BinaryOpExpr(
                                                        RelationOp.LEQ,
                                                        IdNode("x"),
                                                        IdNode("z")
                                                )
                                        ),
                                        Program(CommandStatement(Command.SKIP))
                                ))
                                this
                            }
                    ),
                    arrayOf(
                            "x:=1-1+1",
                            with(Program()) {
                                add(AssignStatement(
                                        IdNode("x"),
                                        BinaryOpExpr(
                                                BinaryArithmeticOp.PLUS,
                                                BinaryOpExpr(
                                                        BinaryArithmeticOp.MINUS,
                                                        NumberNode(1),
                                                        NumberNode(1)
                                                ),
                                                NumberNode(1)
                                        )
                                ))
                                this
                            }
                    ),
                    arrayOf(
                            "x:=1-(1+1)",
                            with(Program()) {
                                add(AssignStatement(
                                        IdNode("x"),
                                        BinaryOpExpr(
                                                BinaryArithmeticOp.MINUS,
                                                NumberNode(1),
                                                BinaryOpExpr(
                                                        BinaryArithmeticOp.PLUS,
                                                        NumberNode(1),
                                                        NumberNode(1)
                                                )
                                        )
                                ))
                                this
                            }
                    )
            )
        }
    }



    @Test fun testAstEqual() {
        var actualAst = parser.parse()
        Assert.assertTrue(AstComparator().compare(actualAst, expectedAst) == 0)
    }
}

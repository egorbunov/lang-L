package ru.spbau.mit.langl

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.spbau.mit.langl.parse.*
import ru.spbau.mit.langl.visitors.AstComparator
import ru.spbau.mit.langl.visitors.Simplifier
import java.io.StringReader
import java.util.*

/**
 * Created by Egor Gorbunov on 22.04.2016.
 * email: egor-mailbox@ya.ru
 */

@RunWith(Parameterized::class)
class OptimizeTest(val program: String, val expectedAst: Program) {
    val parser = ParserWrapper(StringReader(program))

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun getTestData(): Collection<Any> {
            return Arrays.asList(*arrayOf(
                    // Test arithmetic and predicate evaluation
                    arrayOf(
                            "x := -(x == x && y == y) + 1 - 1 * 1 + 1",
                            with(Program()) {
                                add(
                                        AssignStatement(IdNode("x"), NumberNode(0))
                                )
                                this
                            }
                    )),
                    // Test operations with zero optimization
                    arrayOf(
                            "x := y * (0 * x + 0 / x - 0 % x + 0 - 0) + (x - 0)",
                            with(Program()) {
                                add(
                                        AssignStatement(IdNode("x"), IdNode("x"))
                                )
                                this
                            }
                    ),
                    // Test unreachable false-case deleted
                    arrayOf(
                            "if 1 == 1 then if 1 then write TRUE else skip endif else skip endif",
                            with(Program()) {
                                add(
                                        UnaryFunStatement(UnaryFun.WRITE, IdNode("TRUE"))
                                )
                                this
                            }
                    ),
                    // Test unreachable WHILE body deleted (together with wile)
                    arrayOf(
                            "skip; while (((1 + 1 - 1) && x == x && x <= x && y >= y && 1 != 0) - 1) do write UNREACHABLE od",
                            with(Program()) {
                                add(CommandStatement(Command.SKIP))
                                this
                            }
                    )
            )
        }
    }



    @Test fun testAstEqual() {
        var actualAst = parser.parse()!!.accept(Simplifier())
        Assert.assertTrue(AstComparator().compare(actualAst, expectedAst) == 0)
    }
}

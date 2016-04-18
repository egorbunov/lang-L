package ru.spbau.mit.langl

import org.junit.Test
import ru.spbau.mit.langl.parse.ParserWrapper
import java.io.StringReader

/**
 * Created by Egor Gorbunov on 18.04.2016.
 * email: egor-mailbox@ya.ru
 */

class ParserTest {
    @Test fun test1() {
        val parser = ParserWrapper(StringReader("x := 100"))
        val stmnts = parser.parse()
    }
}

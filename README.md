### Edu. purpose lexer / parser / ...

#### Build:

Open in Intellij Idea as maven project or run command under root directory: `mvn clean install`.

After build finished u can run any util(jar file under target dir) with command: `java -jar langle-tools-1.0.jar [ast|lex|pp|opt]`

* `ast` Any given program (from stdin) will be parsed and abstract syntax tree will be shown
* `lex` Given program ---> lexeme array
* `pp` Given program ---> pretty printed given program
* `opt` Given program ---> optimized given program

#### What used

* Kotlin
* JFlex
* CUP
* JUNG


package uk.co.nickthecoder.drunkinvaders

import uk.co.nickthecoder.tickle.AbstractProducer
import uk.co.nickthecoder.tickle.editor.EditorMain
import uk.co.nickthecoder.tickle.groovy.GroovyLanguage

/**
 * The main entry point for the game.
 */
fun main(args: Array<String>) {
    GroovyLanguage().register()
    EditorMain("drunkinvaders", args).start()
}


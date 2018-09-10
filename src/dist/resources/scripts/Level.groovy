import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.events.*

// A Director used by most of the scenes in the game.
// Note, the menu scene also uses this.

class Level extends AbstractDirector {

    // If the scene is won, the next scene to play.
    @Attribute
    public String nextScene = "menu"

    // Initially zero, then incremented as aliens are created.
    // Incremented from method newAlien, and decrmented from alienDead.
    int aliensRemaining = 0

    def ended = false
    def endCountdown = 100

    def escape

    void activated() {
        escape = Resources.instance.inputs.find("escape")
    }

    void tick() {
        if ( escape.isPressed() ) {
            if ( Game.instance.sceneName == "menu" ) {
                Game.instance.quit()
            } else {
                Game.instance.startScene( "menu" )
            }
        }

        if (ended) {
            endCountdown --
            if (endCountdown <= 0) {
                Game.instance.startScene( nextScene )
            }
        }
    }

    void newAlien() {
        aliensRemaining ++
    }

    void alienDied() {
        aliensRemaining --
        if (aliensRemaining <= 0) {
            Game.instance.producer.unlockScene( nextScene )
            ended = true
        }
    }

}


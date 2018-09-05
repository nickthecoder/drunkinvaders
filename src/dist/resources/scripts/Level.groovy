import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.events.*

class Level extends AbstractDirector {

    @Attribute
    public String nextScene = "menu"

    int aliensRemaining = 0

    def ended = false
    def endCountdown = 100

    void tick() {
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
            ended = true
        }
    }

}


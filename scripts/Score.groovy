import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*

class Score extends AbstractRole {

    int oldScore = -1

    void tick() {
        def newScore = Game.instance.producer.score
        if ( newScore != oldScore ) {
            actor.textAppearance.text = newScore.toString().padLeft(6, "0")
        }
    }

}


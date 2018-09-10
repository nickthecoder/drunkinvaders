import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*
import uk.co.nickthecoder.tickle.resources.*

// Change an Actor's role to Dying, and it will gradually fade, and then die.

class Dying extends ActionRole {

    Action createAction() {
        return new Fade( actor.color, 0.5, 0, Eases.linear )
            .and( new Scale( actor, 0.5, 4, Eases.easeIn ) )
            .then( new Kill( actor ) )
    }

}


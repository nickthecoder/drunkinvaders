import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.movement.polar.*
import uk.co.nickthecoder.tickle.action.animation.*

class Shrapnel extends ActionRole {

    Polar2d velocity = new Polar2d( Angle.degrees( Rand.randomDouble(360) ), Rand.randomDouble( 10 ) )

    Action createAction() {
        return new MovePolar( actor.position, velocity )
            .whilst( new Fade( actor.color, 1 /*seconds*/, 0 /* invisible */, Eases.easeOut ) )
            .then( new Kill( actor ) )
    }

}


import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.movement.*
import uk.co.nickthecoder.tickle.action.animation.*
import uk.co.nickthecoder.tickle.action.Kill

// Change an Actor's role to Dying, and it will gradually fade, and then die.
// It also spurts out "shrapnel". The actor's costume must have an event named "shrapnel",
// with an appropriate Pose.
//
// The three aliens have white shrapnel, the Mothership has red shrapnel, and Ship has
// green shrapnel.

class DyingWithShrapnel extends Dying {

    int fragments

    DyingWithShrapnel() {
        fragments = 20
    }

    DyingWithShrapnel( int amount ) {
        fragments = amount
    }

    void activated() {
        super.activated()

        for ( int i = 0; i < fragments; i ++ ) {
            def shrapnelA = actor.createChild( "shrapnel" )
        }

        FragmentMaker.createActionRoles(actor, "fragments", { actor, offset -> 
            double seconds = 30.0
            new Move(actor.position, offset.mul(3))
                .and(new Turn(actor.direction, 1.0, Angle.degrees(Rand.between( -100, 100 ))).forever())
                .whilst(new Fade(actor.color, seconds, 0f, Eases.easeInQuad))
                .then(new Kill(actor))
        })
    }

}

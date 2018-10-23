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

        FragmentMaker.createRoles(actor, "fragments", { actor, offset -> 
            double seconds = 30.0
            new Move(actor.position, offset.mul(3))
                //.and(new Accelerate(velocity, acceleration))
                .and(new Turn(actor.direction, seconds, Angle.degrees(Rand.between( -1000, 1000 ))))
                .whilst(new Fade(actor.color, seconds, 0f, Eases.easeInQuad))
                .then(new Kill(actor))
        })
    }

}

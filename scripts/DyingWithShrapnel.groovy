import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*

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
    }

}


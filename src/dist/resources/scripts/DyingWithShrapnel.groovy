import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*

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


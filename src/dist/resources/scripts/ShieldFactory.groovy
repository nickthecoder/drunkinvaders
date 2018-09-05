import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*

class ShieldFactory extends AbstractRole {

    def void begin() {
        def across = 8
        def down = 4
        for ( def x = 0; x < across; x ++ ) {
            for ( def y = 0; y < down; y ++ ) {
                def name = "shield"
                if ( y == 0 ) {
                    if ( x == 0 ) name = "shieldNW"
                    if ( x == across - 1 ) name = "shieldNE"
                }
                def shieldA = actor.createChild( name )
                shieldA.direction.radians = actor.direction.radians
                shieldA.moveForwards( (x - across / 2) * 10 )
                shieldA.moveSidewards( (y - down / 2) * 10 )
            }
        }
        actor.die()
    }

    def void tick() {
    }
}


import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import org.joml.*

class Shield extends AbstractRole implements Enemy, Friend, Bounces {

    def velocity = new Vector2d( 0, 0 )
    def mass = 100

    void tick() {
    }
    
    void hit() {
        actor.event( "hit" )
        actor.role = new Dying()
    }

    void bounce( double impact ) {
        actor.role = new Dying()
    }

}


import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import org.joml.*

class Shield extends AbstractRole implements Alien, Human, Bounces {

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


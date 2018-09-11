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
        // Have we been hit hard enough to be destroyed?
        if (impact > 0.5) {
            actor.role = new Dying()
        }
    }

}


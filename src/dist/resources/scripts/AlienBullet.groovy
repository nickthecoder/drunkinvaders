import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*

class AlienBullet extends AbstractRole implements Alien {

    @Attribute
    public double speed = 3

    def animation

    void begin() {
        animation = 
            (new Delay( 0.1 )
            .then( new EventAction( actor, "other" ) )
            .then( new Delay( 0.1 ) )
            .then( new EventAction( actor, "default" ) ) ).forever()
    }

    void tick() {

        animation.act()

        actor.moveForwards( speed )

        def pixel = Game.instance.producer.pixel

        for( def other : actor.stage.findRolesByClass( Human.class ) ) {
            if (pixel.overlapping( actor, other.actor ) ) {
                if ( other instanceof Ship && other.shielded ) {
                    actor.role = new Dying()
                } else {
                    other.hit()
                    actor.die()
                }
                break
            }
        }

        if ( actor.x < 0 || actor.y < 0 || actor.x > 640 || actor.y > 480 ) {
            actor.die()
        }
    }

    void hit() {
        actor.die()
    }

}


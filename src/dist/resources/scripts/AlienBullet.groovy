import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*

class AlienBullet extends AbstractRole implements Enemy {

    def animation

    def void begin() {
        animation = 
            (new Delay( 0.1 )
            .then( new EventAction( actor, "other" ) )
            .then( new Delay( 0.1 ) )
            .then( new EventAction( actor, "default" ) ) ).forever()
    }

    def void tick() {

        animation.act()

        actor.moveForwards( 3 )

        def pixel = Game.instance.producer.pixel

        for( def other : actor.stage.findRolesByClass( Friend.class ) ) {
            if (pixel.overlapping( actor, other.actor ) ) {
                other.hit()
                actor.die()
                break
            }
        }

        if ( actor.x < 0 || actor.y < 0 || actor.x > 640 || actor.y > 480 ) {
            actor.die()
        }
    }

    def void hit() {
        actor.die()
    }

}


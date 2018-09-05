import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*

class Ship extends AbstractRole implements Friend {

    def left = Resources.instance.inputs.find("left")
    def right = Resources.instance.inputs.find("right")
    def fire = Resources.instance.inputs.find("fire")

    def bulletA = null

    def void tick() {

        if (left.isPressed()) {
            actor.x -= 4
        }
        if (right.isPressed()) {
            actor.x += 4
        }

        if ( bulletA != null ) {
            if ( bulletA.stage == null ) {
                actor.event( "reloaded" )
                bulletA = null
            }
        } else {
            if (fire.isPressed() ) {
                actor.event( "fire" )
                bulletA = actor.createChild("bullet")
                bulletA.direction = actor.direction
                bulletA.moveForwards( 16 )
            }
        }

        def pixel = Game.instance.producer.pixel

        for( def other : actor.stage.findRolesByClass( Enemy.class ) ) {
            if ( pixel.overlapping( actor, other.actor ) ) {
                actor.die()
            }
        }

    }

    def void hit() {
        actor.role = new Dying()
    }
}


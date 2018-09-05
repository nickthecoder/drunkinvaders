import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.collision.*
import org.joml.*

class Alien extends AbstractRole implements Enemy, Bounces {

    def velocity = new Vector2d( 0, 0 )
    def spin = 0

    def radius = 16
    def mass = 1

    def fireRate = 100

    def rand = new RandomFactory()

    def void tick() {

        actor.position.add( velocity )
        if ( spin > 5 ) spin = 5
        if ( spin < -5) spin = -5
        actor.direction.degrees += spin
        
        if ( actor.x > 640 - radius && velocity.x > 0 ) {
            velocity.x = - velocity.x
        }
        if ( actor.x < radius && velocity.x < 0 ) {
            velocity.x = - velocity.x
        }
        if ( actor.y > 480 - radius && velocity.y > 0 ) {
            velocity.y = - velocity.y
        }
        if ( actor.y < radius && velocity.y < 0 ) {
            velocity.y = - velocity.y
        }

        if ( rand.oneIn( fireRate ) ) {
            fire()
        }

        def pixel = Game.instance.producer.pixel

        for( def other : actor.stage.findRolesByClass( Bounces.class ) ) {
            if ( other != this && pixel.overlapping( actor, other.actor ) ) {

                def impact = CollisionKt.circularCollision(
                    actor.position, velocity, mass,
                    other.actor.position, other.velocity, other.mass )

                spin += rand.plusMinus( impact )
                other.bounce(impact)
            }
        }

    }

    def fire() {
        def bulletA = actor.createChild( "bullet" )
        bulletA.direction.radians = actor.direction.radians
        bulletA.moveForwards( 10 )
    }

    def void hit() {
        actor.role = new Dying()
    }

    def void bounce( double impact ) {
        spin += rand.plusMinus( impact )
    }

}



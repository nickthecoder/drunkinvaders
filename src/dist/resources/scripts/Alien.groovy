import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.collision.*
import org.joml.*

class Alien extends AbstractRole implements Enemy, Bounces {

    @Attribute
    public int fireFrequency = 100

    @Attribute
    public Vector2d velocity = new Vector2d( 0, 0 )

    @Attribute
    public double spin = 0

    @Attribute
    public double bulletSpeed = 3

    def radius = 16
    def mass = 1

    def rand = new RandomFactory()

    void activated() {
        Game.instance.director.newAlien()
    }

    void tick() {

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

        if ( rand.oneIn( fireFrequency ) ) {
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

    void fire() {
        def bulletA = actor.createChild( "bullet" )
        bulletA.direction.radians = actor.direction.radians
        bulletA.moveForwards( 10 )
        bulletA.role.speed = bulletSpeed
    }

    void hit() {
        Game.instance.director.alienDied()
        actor.role = new Dying()
    }

    void bounce( double impact ) {
        spin += rand.plusMinus( impact )
    }

}



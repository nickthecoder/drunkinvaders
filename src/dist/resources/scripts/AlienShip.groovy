import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.collision.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*
import org.joml.*

class AlienShip extends AbstractRole implements Alien, Bounces {

    @Attribute
    public int fireFrequency = 100

    @Attribute
    public Vector2d velocity = new Vector2d( 0, 0 )

    @Attribute
    public double spin = 0

    @Attribute
    public double bulletSpeed = 3

    @CostumeAttribute
    public int points = 10

    def radius = 16
    def mass = 1

    def scaleAction = null

    def rand = new RandomFactory()

    // An Action, which cycles through the frames (i.e. chaing pose)
    Action animation = null

    void activated() {
        Game.instance.director.newAlien()
        animation = createAnimation().forever()
        animation.begin()
    }

    Action createAnimation() {
        return new Delay( 0.2 )
            .then( new EventAction( actor, "frame1" ) )
            .then( new Delay( 0.2 ) )
            .then( new EventAction( actor, "default" ) )
    }

    void tick() {

        animation.act()

        if ( scaleAction != null ) {
            if (scaleAction.act()) {
                scaleAction = null
            }
        }

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
                bounceWith( other )
            }
        }

        for( def other : actor.stage.findRolesByClass( Ship.class ) ) {
            if ( other != this && pixel.overlapping( actor, other.actor ) ) {
                if (other.shielded) {
                    bounceWith( other )
                } else {
                    other.hit()
                }
            }
        }

    }

    void bounceWith( Bounces other ) {
        def impact
        if ( other instanceof AlienShip ) {
            impact = CollisionKt.circularCollision(
                actor.position, velocity, mass,
                other.actor.position, other.velocity, other.mass )
        } else {
            impact = CollisionKt.circularCollision(
                actor.position, velocity, mass,
                other.actor.position, new Vector2d(0,0), 100 )
        }
        
        spin += rand.plusMinus( impact )
        other.bounce(impact)
    }

    void fire() {
        def bulletA = actor.createChild( "bullet" )
        bulletA.direction.radians = actor.direction.radians
        bulletA.moveForwards( 10 )
        bulletA.role.speed = bulletSpeed
    }

    void hit() {
        if ( actor.scaleXY > 1 ) {
            scaleAction = new Scale( actor, 1, actor.scaleXY * 0.9, Eases.easeInOut )
        } else {
            Game.instance.director.alienDied()
            Game.instance.producer.score += points
            actor.role = new DyingWithShrapnel()
        }
    }

    void bounce( double impact ) {
        spin += rand.plusMinus( impact )
    }

}



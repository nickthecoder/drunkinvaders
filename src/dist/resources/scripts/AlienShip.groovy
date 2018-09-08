import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.collision.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*
import org.joml.*

// AlienShips can be placed in a Scene individually, or created on-mass by AlienFactory.
// They can also be created from a Mothership.
//
// There are three different Costumes, which use this Role. In addition, Mothership
// extends AlienShip. It behaves the same, but also gives birth to more AlienShips.
//
// The move and spin, and bounce off the edges of the screen, other AlienShips and Shields.
// They also bounce off of Ship when it is shielded.
// The direction of movement is unrelated to the angle of their image (they are drunk!).
//
// AlienBullets are fired at random intervals.

class AlienShip extends AbstractRole implements Alien, Bounces {

    @Attribute
    public int fireFrequency = 100

    @Attribute
    public Vector2d velocity = new Vector2d( 0, 0 )

    @Attribute
    public double spin = 0

    @Attribute
    public double bulletSpeed = 3

    // Points awarded when this AlienShip is shot.
    // By used "@CostumeAttribute", the 3 different alien types can be assigned a
    // different number of points in the Editor's Costume.
    @CostumeAttribute
    public int points = 10

    // Used as a rough indication of size, when bouncing off the edges of the screen.
    // It is NOT used when colliding with other Actors.
    @CostumeAttribute
    public int radius = 16

    // Used when colliding with other AlienShips.
    @CostumeAttribute
    public double mass = 1


    // private attributes


    // Changes the image size.
    // Only used when an AlienShip is created from a MotherShip.
    Action scaleAction = null

    // An Action, which cycles through the frames (i.e. changing pose)
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

        if ( Rand.oneIn( fireFrequency ) ) {
            fire()
        }

        // Get the collision algorithm, shared throughout the game.
        def pixel = Game.instance.producer.pixel

        // Look for other AlienShips, Shields and Ships (all implement Bounces)
        for( def other : actor.stage.findRolesByClass( Bounces.class ) ) {
            if ( other != this && pixel.overlapping( actor, other.actor ) ) {

                // Unshielded ships must be handled differently.
                if (other instanceof Ship && !other.shielded) {
                    other.hit()
                } else {
                    bounceWith( other )
                }

            }
        }
    }

    void bounceWith( Role other ) {

        Vector2d otherVelocity
        double otherMass

        // Only AlienShips have a velocity and mass (Ship and Shield don't),
        // so we need to treat them differently.
        if ( other instanceof AlienShip ) {
            otherVelocity = other.velocity
            otherMass = other.mass
        } else {
            otherVelocity = new Vector2d( 0,0 ) // Assume they aren't moving
            otherMass = 1000 // Very heavy, so the AliedShip bouces away
        }

        // Collides two obects, as if they were perfectly round, without friction.
        // The resulting "impact" is high for fast, head-on collisions, and low for
        // slow or glancing collisions.
        def impact = CollisionKt.circularCollision(
                actor.position, velocity, mass,
                other.actor.position, otherVelocity, otherMass )

        // Changes the rate we are spinning by a random amount
        // This isn't anything like physically correct! However, it make the game look good ;-)
        spin += Rand.plusMinus( impact )

        // Let the other guy know that it has been bounced (it may emit a sound, or change spin etc).
        other.bounce(impact)
    }

    // Called when another AlienShip bounces against this one.
    void bounce( double impact ) {
        spin += Rand.plusMinus( impact )
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

}



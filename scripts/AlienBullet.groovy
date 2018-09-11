import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*

// Fired by AlienShip
class AlienBullet extends AbstractRole implements Alien {

    // The speed of the bullet in pixels per tick.
    // Note, the direction is governed by Actor.direction.
    @Attribute
    public double speed = 3

    // An Action, which animates the bullet (it has two different poses)
    // tick() by call animation.act() for it to take effect.
    Action animation

    void begin() {
        animation = 
            (new Delay( 0.1 )
            .then( new EventAction( actor, "other" ) )
            .then( new Delay( 0.1 ) )
            .then( new EventAction( actor, "default" ) ) ).forever()
        animation.begin()
    }

    void tick() {

        // Alternate between the two Poses
        animation.act()

        actor.moveForwards( speed )

        // Get the collision algorithm, shared throughout the game.
        def pixel = Game.instance.producer.pixel

        // Look for everything that this bullet can hit, and 
        // check if we are overlapping them.
        for( def other : actor.stage.findRolesByClass( Human.class ) ) {
            if (pixel.overlapping( actor, other.actor ) ) {

                // Have we hit a shielded ship?
                if ( other instanceof Ship && other.shielded ) {
                    // Change roles to Dying rather than just actor.die()
                    // for a nice effect
                    actor.role = new Dying()
                    actor.event( "hitShielded" ) // Play a sound???
                } else {
                    
                    // Note, we let the "other" make a sound effect.
                    other.hit()
                    actor.die() // Die instantly, without the nice effect.
                }
                // If we are overlapping two things simultaneously, only hit the first one we find.
                break // (Exit the for loop)
            }
        }

        // Have we left the screen?
        if ( actor.x < 0 || actor.y < 0 || actor.x > 640 || actor.y > 480 ) {
            actor.die()
        }
    }

    // Called when we've been hit by a (human) Bullet
    void hit() {
        actor.event("die")
        actor.die()
    }

}


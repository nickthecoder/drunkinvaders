import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

class Ship extends AbstractRole implements Human, Bounces {

    // "@Attribute" means that these attributes can be changed in the Scene Editor.

    // The center of the planet/moon
    @Attribute
    public Vector2d center = new Vector2d( 320, -1000 )

    // The speed the ship moves (in degrees, as we move in circles)
    @Attribute
    public double speedDegrees = 0.2

    // The speed of our bullets
    @Attribute
    public double bulletSpeed = 6

    @Attribute
    public int shieldTicks = 1000

    @Attribute
    public boolean canMoveShielded = false

    @Attribute( rows=4 )
    public String message = ""

    int orbit = 0 // Distance from the center of the planet/moon. Set in activated()
    int radius = 26 // The size of the ship (use when checking if the ship tries to move off-screen).

    int shieldUsed = 0 // Number of "ticks" that the shield has been used.
    boolean shielded = false // Is the shield on?

    // Keyboard inputs. Set in activated() method.
    // Note, the keys used are defined in the "inputs" section from within the Tickle editor.
    // Note, more than one key can be assigned to each input.
    def left    // Left arrow key, and "A"
    def right   // Right arrow key ans "D"
    def fire    // Space, Return, Up arrow key and "W"
    def shield  // Ctrl, Shift, Down arrow key and "S"
    def suicide

    // Our bullet's Actor (not the Role).
    // Set in fire(), and set back to null in tick(), after the bullet has died
    // (moved off screen, or hit something)
    Actor bulletA = null 

    // Called when the scene first starts. We initialise stuff here.
    void activated() {
        left = Resources.instance.inputs.find("left")
        right = Resources.instance.inputs.find("right")
        fire = Resources.instance.inputs.find("fire")
        shield = Resources.instance.inputs.find("shield")
        suicide = Resources.instance.inputs.find("escape")

        orbit = actor.position.distance( center )
        actor.direction.radians = Angle.radiansOf( actor.position, center )

        if (message!= "") {
            Talk.talk( actor, message, 3 )            
        }
    }

    // Called 60 times per second. Check the keyboard inputs, and act appropriately.
    void tick() {

        if (suicide.isPressed()) {
            hit()
        }

        if (shield.isPressed() && shieldUsed < shieldTicks) {

            shieldUsed ++
            if ( shieldUsed >= shieldTicks ) {
                shielded = false
            } else {
                shielded = true
            }
            choosePose()

        } else {
            if (shielded) {
                shielded = false
                choosePose()
            }
        }

        if ( ! shielded || canMoveShielded ) {
            if (left.isPressed()) {
                rotate( speedDegrees )
            }
            if (right.isPressed()) {
                rotate( -speedDegrees )
            }
        }

        // Have we fired a bullet? (Only one human bullet allowed on screen at a time!)
        if ( bulletA != null ) {
            
            if ( bulletA.stage == null ) {
                // If the bullet we fired is destroyed, then reload, ready to fire again.
                bulletA = null
                choosePose()
            }
        } else {

            if (fire.isPressed() ) {
                actor.event( "fire" )
                bulletA = actor.createChild("bullet")
                bulletA.direction = actor.direction
                bulletA.moveForwards( 16 )
                bulletA.role.speed = bulletSpeed

                // Slow bullets are large, fast bullets are small
                bulletA.scaleXY = 12 / bulletSpeed

                // Change pose (to show we are reloading (and cannot fire yet)
                choosePose()
            }
        }

    }

    // Changes the ships appearance.
    // If shielded, pick the pose to indicate the shield level ("shield<N>")
    // Otherwise, show if the ship is ready to fire or not ("reloaded" or "reloading")
    void choosePose() {
        if (shielded) {
            int frame = (8 * shieldUsed)/shieldTicks
            actor.event( "shield" + frame )
        } else {
            if ( bulletA == null ) {
                // Note, this event could also have a sound associated with it, so
                // we can HEAR that we've reloaded.
                actor.event( "reloaded" )
            } else {
                actor.event( "reloading" )
            }   
        }
    }

    // Move the ship in a circle (around the planet/moon)
    void rotate( double degrees ) {
        actor.direction.degrees += degrees
        actor.position.set( center )
        actor.position.add( actor.direction.vector().mul( orbit ) )

        // If rotated too far, reset to the previous position
        if ( (actor.x < radius) || (actor.x > 640 - radius) ||
            (actor.y < radius) || (actor.y > 480 - radius) ) {

            actor.direction.degrees -= degrees   
            actor.position.set( center )
            actor.position.add( actor.direction.vector().mul( orbit ) )
        }
    }

    // Called when hit by an AlienBullet, or an AlienShip
    void hit() {
        actor.role = new DyingWithShrapnel(100)

        def life = actor.stage.findRolesByClass( Life.class ).max { it.index }
        if ( life == null ) {
            // Game over!
        } else {
            life.rebirth( this )
            Game.instance.producer.lives --
        }

    }

    // An alien ship has hit us while our shield is on. It will bounce away harmlessly.
    // We don't need to do anything, but could make a sound, or talk, reduce our shield etc.
    void bounce( double impact ) {
    }

}


import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

class Ship extends AbstractRole implements Human, Bounces {

    @Attribute
    public Vector2d center = new Vector2d( 320, -1000 )

    @Attribute
    public double speedDegrees = 0.2

    @Attribute
    public double bulletSpeed = 6

    @Attribute
    public int shieldTicks = 100

    def radius = 0
    def mass = 100

    int shieldUsed = 0
    boolean shielded = false

    // Keyboard inputs (set in activated)
    def left 
    def right
    def fire 
    def shield

    def bulletA = null

    void activated() {
        left = Resources.instance.inputs.find("left")
        right = Resources.instance.inputs.find("right")
        fire = Resources.instance.inputs.find("fire")
        shield = Resources.instance.inputs.find("shield")

        radius = actor.position.distance( center )
        actor.direction.radians = Angle.radiansOf( actor.position, center )
    }

    void choosePose() {
        if (shielded) {
            int frame = (8 * shieldUsed)/shieldTicks
            actor.event( "shield" + frame )
        } else {
            if ( bulletA == null ) {
                actor.event( "reloaded" )
            } else {
                actor.event( "reloading" )
            }   
        }
    }

    void tick() {

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

        if (left.isPressed()) {
            actor.direction.degrees += speedDegrees
            actor.position.set( center )
            actor.position.add( actor.direction.vector().mul( radius ) )
        }
        if (right.isPressed()) {
            actor.direction.degrees -= speedDegrees
            actor.position.set( center )
            actor.position.add( actor.direction.vector().mul( radius ) )
        }

        if ( bulletA != null ) {
            if ( bulletA.stage == null ) {
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
                bulletA.scaleXY = 12 / bulletSpeed
                choosePose()
            }
        }

    }

    void hit() {
        actor.role = new Dying()
    }

    // An alien ship has hit us while our shield is on. It will bounce away,
    // We don't need to do anything, but could make a sound, or talk, reduce our shield etc.
    void bounce( double impact ) {
    }

}


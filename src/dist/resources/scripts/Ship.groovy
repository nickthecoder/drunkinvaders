import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

class Ship extends AbstractRole implements Friend {

    @Attribute
    public Vector2d center = new Vector2d( 320, -1000 )

    @Attribute
    public double speedDegrees = 0.2

    @Attribute
    public double bulletSpeed = 6

    def left = Resources.instance.inputs.find("left")
    def right = Resources.instance.inputs.find("right")
    def fire = Resources.instance.inputs.find("fire")

    def radius = 0

    def bulletA = null

    void activated() {
        radius = actor.position.distance( center )
        actor.direction.radians = Angle.radiansOf( actor.position, center )
    }

    void tick() {

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
                actor.event( "reloaded" )
                bulletA = null
            }
        } else {
            if (fire.isPressed() ) {
                actor.event( "fire" )
                bulletA = actor.createChild("bullet")
                bulletA.direction = actor.direction
                bulletA.moveForwards( 16 )
                bulletA.role.speed = bulletSpeed
                bulletA.scaleXY = 12 / bulletSpeed
            }
        }

        def pixel = Game.instance.producer.pixel

        for( def other : actor.stage.findRolesByClass( Enemy.class ) ) {
            if ( pixel.overlapping( actor, other.actor ) ) {
                hit()
            }
        }

    }

    void hit() {
        actor.role = new Dying()
    }
}


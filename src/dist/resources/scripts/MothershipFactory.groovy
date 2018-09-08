import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.action.*
import org.joml.*

class MothershipFactory extends ActionRole {

    @Attribute
    public double delay = 0

    @Attribute
    public int ships = 3

    @Attribute
    public double interval = 10
    
    @Attribute
    public int mothershipBirthPeriod = 300

    @Attribute
    public int mothershipMaxChildren = 10

    @Attribute
    public int mothershipFireFrequency = 100

    @Attribute
    public Vector2d mothershipVelocity = new Vector2d( 0, 0 )

    @Attribute
    public double mothershipBulletSpeed = 3


    Action createAction() {
        return new Delay( delay )
            .then(
                new Do( { createMothership() } )
                .then( new Delay( interval ) )
            ).repeat( ships )
    }

    void createMothership() {
        def mothershipA = actor.createChild( "mothership" )
        mothershipA.direction.radians = actor.direction.radians

        def mothership = mothershipA.role
        mothership.birthPeriod = mothershipBirthPeriod
        mothership.maxChildren = mothershipMaxChildren
        mothership.fireFrequency = mothershipFireFrequency
        mothership.bulletSpeed = mothershipBulletSpeed
        // NOTE, we cannot use "=", because then all of the motherships would SHARE the
        // same velocity, they wouldn't move independantly.
        mothership.velocity.set( mothershipVelocity )
    }
}


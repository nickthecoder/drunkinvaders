import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

class AlienFactory extends AbstractRole {

    // Public attributes (Can be changed in the SceneEditor)

    @Attribute
    public int across = 3

    @Attribute
    public int down = 2

    @Attribute
    public double countdown = 20

    @Attribute
    public double delay = 5

    @Attribute
    public double alienSpeed = 3

    @Attribute
    public int fireFrequency = 100

    @Attribute
    public int bulletSpeed = 3

    // Private fields

    int ySpacing = 50
    int xSpacing = 60

    def x = 0
    def y = 0

    def aliens = new ArrayList()

    void activated() {
        actor.hide()
    }

    void tick() {
        countdown --
        if ( countdown <= 0 ) {

            // Create a new alien
            def alienA = actor.createChild("row" + y)
            alienA.direction.radians = actor.direction.radians
            alienA.moveSidewards( - x * xSpacing )
            alienA.moveForwards( y * ySpacing )
            aliens.add( alienA.role )

            x ++
            if ( x >= across ) {
                x = 0
                y ++
                if ( y >= down ) {
                    // All aliens created. Let's set them in motion
                    for ( def alien in aliens ) {
                         alien.velocity.set( actor.direction.vector().perpendicular().mul( -alienSpeed ) )
                         alien.fireFrequency = fireFrequency
                         alien.bulletSpeed = bulletSpeed
                    }
                    actor.die()
                }
            }
            countdown = delay
        }

    }

}


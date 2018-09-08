import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

// Creates a grid of AlienShips
// It would be a pain to place every single AlienShip on every Scene by hand!
//
// The AlienShips are not created all at once, but one after another (with a delay).

class AlienFactory extends AbstractRole {

    // Public attributes (Can be changed in the SceneEditor)

    // Number of columns of AlienShips
    @Attribute
    public int across = 3

    // Number of rows of AlienShips
    @Attribute
    public int down = 2

    // The initial delay before the first AlienShip is created
    // Measured in ticks (1/60th of a second)
    @Attribute
    public double countdown = 20

    // The delay between creating one AlienShip and the next.
    // Measured in ticks (1/60th of a second)
    @Attribute
    public double delay = 5

    // The distance between the rows. Note, could be negative to create them right to left.
    @Attribute
    public int spacingX = 50

    // The distance between the columns. Nout, could be negative to created them bottom to top.
    @Attribute
    public int spacingY = 60

    // The following attributes are applied to all of the AlienShips that are created.

    @Attribute
    public double alienSpeed = 3

    @Attribute
    public int fireFrequency = 100

    @Attribute
    public int bulletSpeed = 3


    // Private fields

    // Keeps track of where the next AlienShip has to go.
    def x = 0
    def y = 0

    def aliens = new ArrayList()

    void activated() {
        // We don't want to SEE the AlienFactory while the game is playing.
        actor.hide()
    }

    void tick() {
        countdown --
        if ( countdown <= 0 ) {

            // Create a new alien
            def alienA = actor.createChild("row" + y)
            alienA.direction.radians = actor.direction.radians
            alienA.moveSidewards( - x * spacingX )
            alienA.moveForwards( y * spacingY )
            aliens.add( alienA.role )
            // Note, at this point, the AlienShip's velocity will be (0,0), so it won't move.

            x ++
            if ( x >= across ) {
                // Move to the next row.
                x = 0
                y ++
                if ( y >= down ) {
                    // All aliens created. Let's set them in motion
                    for ( def alien in aliens ) {
                         alien.velocity.set( actor.direction.vector().perpendicular().mul( -alienSpeed ) )
                         alien.fireFrequency = fireFrequency
                         alien.bulletSpeed = bulletSpeed
                    }
                    // The AlienFactory has done its job!
                    actor.die()
                }
            }
            // Wait for "delay" ticks till we create the next AlienShip.
            countdown = delay
        }

    }

}


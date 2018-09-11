import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.util.*
import org.joml.*

// Creates a grid of AlienShips
// It would be a pain to place every single AlienShip on every Scene by hand!
//
// The AlienShips are not created all at once, but one after another (with a delay).

class AlienFactory extends ActionRole {

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
    public double initialDelay = 1

    // The delay between creating one AlienShip and the next.
    // Measured in ticks (1/60th of a second)
    @Attribute
    public double delay = 0.1

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
        super.activated()
        // We don't want to SEE the AlienFactory while the game is playing.
        actor.hide()
    }

    Action createAction() {
        return new Delay( initialDelay )
            .then( 
                ( new Do( { createShip() } ).then( new Delay( delay ) ) ).repeat( down * across )
            )
            .then( new Do( { setInMotion() } ) )
            .then( new Kill(actor) )
    }

    void createShip() {

        actor.event( "birth" )
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
        }
    }

    void setInMotion() {
        // All aliens created. Let's set them in motion
        for ( def alien in aliens ) {
             alien.velocity.set( actor.direction.vector().perpendicular().mul( -alienSpeed ) )
             alien.fireFrequency = fireFrequency
             alien.bulletSpeed = bulletSpeed
        }
    }

}


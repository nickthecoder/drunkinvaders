import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import org.joml.*

class AlienFactory extends AbstractRole {

    def across = 3
    def down = 2
    def ySpacing = 50
    def xSpacing = 60

    def countdown = 20
    def delay = 5
    def x = 0
    def y = 0

    def speed = 3

    def aliens = new ArrayList()

    def void activated() {
        actor.hide()
    }

    def void tick() {
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
                         alien.velocity.set( actor.direction.vector().perpendicular().mul( -speed ) )
                    }
                    actor.die()
                }
            }
            countdown = delay
        }

    }

}


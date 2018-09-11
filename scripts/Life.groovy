import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*
import uk.co.nickthecoder.tickle.action.movement.*
import org.joml.Vector2d

// Displays the number of additional lives remaining

class Life extends AbstractRole {

    int index = 0

    Ship oldShip = null

    Action animation = null

    void tick() {
        if (animation != null) {
            if (animation.act()) {
                Ship ship = new Ship()
                ship.bulletSpeed = oldShip.bulletSpeed
                ship.speedDegrees = oldShip.speedDegrees
                ship.shieldTicks = oldShip.shieldTicks
                ship.canMoveShielded = oldShip.canMoveShielded
                ship.center.set( oldShip.center )
                ship.radius = oldShip.radius
                ship.shielded = true
                actor.role = ship
            }
        }
    }

    void rebirth( Ship ship  ) {
        oldShip = ship
        def seconds = 2

        // Flash on and off (this will be repeated, see below)
        def flash =
            new EventAction(actor, "flashOn")
            .then( new Delay(0.1) )
            .then( new EventAction(actor, "flashOff") )
            .then( new Delay(0.1) )

        // The amount the ship turns whilest moving into position.
        // It is nicer looking if it always does and extra spin (360).
        def turn = Angle.degrees(360) + ship.actor.direction - actor.direction
        if ( turn.degrees < 180 ) turn += Angle.degrees( 360 )

        // Return to normal size, move to the correct place, and spin a little bit.
        def movement =
            new Scale( actor, 1, new Vector2d(1,1), Eases.easeInQuad )
            .and( new MoveTo( actor.position, seconds, ship.actor.position, Eases.easeInOut) )
            .and( new Turn( actor.direction, seconds, turn, Eases.easeOut) )

        // Do "flash" 5 times, and then do "movement"
        // The
        animation = 
            flash.repeat( 5 )
            .then( new EventAction( actor, "shield0") )
            .then( movement )
            .then( new EventAction( actor, "default") )

        animation.begin()
    }

}


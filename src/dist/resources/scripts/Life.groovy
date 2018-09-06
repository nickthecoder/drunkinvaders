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
    double bulletSpeed
    int shieldTicks

    Action animation = null

    void tick() {
        if (animation != null) {
            if (animation.act()) {
                actor.role = new Ship()
                actor.role.bulletSpeed = bulletSpeed
                actor.role.shieldTicks = shieldTicks
            }
        }
    }

    void rebirth( Ship ship  ) {
        this.bulletSpeed = ship.bulletSpeed
        this.shieldTicks = ship.shieldTicks

        def seconds = 2

        def flash =
            new EventAction(actor, "flashOn")
            .then( new Delay(0.1) )
            .then( new EventAction(actor, "flashOff") )
            .then( new Delay(0.1) )

        def movement =
            new Scale( actor, 1, new Vector2d(1,1), Eases.easeInQuad )
            .and( new MoveTo( actor.position, seconds, ship.actor.position, Eases.easeInOut) )
            .and( Turn.turnTo( actor.direction, seconds, ship.actor.direction, Eases.easeInQuad) )

        animation = 
            flash.repeat( 5 )
            .then( new EventAction( actor, "shield0") )
            .then( movement )
            .then( new EventAction( actor, "default") )

        animation.begin()
    }

}


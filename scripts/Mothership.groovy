import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*

class Mothership extends AlienShip {

    @Attribute
    public int birthPeriod = 300

    @Attribute
    public int maxChildren = 10

    Action createAnimation() {
        return new Delay( 0.2 )
            .then( new EventAction( actor, "frame1" ) )
            .then( new Delay( 0.2 ) )
            .then( new EventAction( actor, "frame2" ) )
            .then( new Delay( 0.2 ) )
            .then( new EventAction( actor, "default" ) )
    }

    void tick() {
        super.tick()
        
        if (maxChildren > 0 && Rand.oneIn( birthPeriod )) {

            maxChildren --
            actor.event( "birth" )
            Actor shipA = actor.createChild( "child" )

            // Make the child come out from the bottom of the mothership
            shipA.direction.radians = actor.direction.radians
            shipA.moveForwards( radius * 0.3 )

            // The child moves directly away from the mother, but also adding the
            // mothers velocity.
            shipA.position.sub( actor.position, shipA.role.velocity )
            shipA.role.velocity.mul( 0.3 ).add( velocity )

            // Make the child very small at first, and begin an action to scale it up
            shipA.scaleXY = actor.scaleXY * 0.1
            shipA.role.scaleAction = new Scale( shipA, 1, actor.scaleXY, Eases.easeOut )
            shipA.role.scaleAction.begin()
        }
    }

}


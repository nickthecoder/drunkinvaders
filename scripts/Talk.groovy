import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.animation.*
import uk.co.nickthecoder.tickle.action.movement.*

import org.joml.*

class Talk extends Follower {

    def seconds
    Angle angle

    Talk( Actor follow, Vector2d offset, Double seconds, Angle angle ) {
        super( follow, offset )
        this.seconds = seconds
        this.angle = angle
    }

    Action createAction() {
        return super.createAction()
            .and( new FollowAngle( actor, follow, angle ) )
            .whilst( 
                new Delay( seconds ).then( new Fade( actor.color, 1, 0, Eases.easeIn ) )
            )
            .then( new Kill( actor ) )
    }

    static void talk( Actor speaker, String message, Double seconds ) {
        def talkA = speaker.createChild( "talk" )
        talkA.textAppearance.text = message
        def above = 40
        talkA.role = new Talk( speaker, new Vector2d( above, 0 ), seconds, Angle.degrees(-90) )

        def bubbleA = speaker.createChild( "bubble" )
        bubbleA.ninePatchAppearance.size.x = talkA.textAppearance.width() + 20
        bubbleA.ninePatchAppearance.size.y = talkA.textAppearance.height() + 30
        bubbleA.role = new Talk( speaker, new Vector2d( above-18, 0 ), seconds, Angle.degrees(-90) )
        bubbleA.zOrder = talkA.zOrder - 0.1
        bubbleA.color.alpha = 0.7
    }
}


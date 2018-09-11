import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.action.*
import uk.co.nickthecoder.tickle.action.movement.*
import uk.co.nickthecoder.tickle.action.animation.*
import org.joml.*

// Animates Buttons. Used with the buttons with costumes quitButton, sceneButton.
class ButtonTwirls extends ExampleButtonEffects {

    Action clicked(Button button) {
        return super.clicked( button ).and (
                new EventAction( button.actor, "click" )
            ). and (
                new MoveTo(
                    button.actor.position,
                    0.5,
                    new Vector2d( 320, 240 ),
                    Eases.easeIn
                )
            ). and (
                new Fade(
                    button.actor.color,
                    0.6,
                    0,
                    Eases.easeIn
                )
            )
    }

    Action enter( Button button) {
        return new EventAction( button.actor, "enter" ).and( super.enter( button ) )
    }
}


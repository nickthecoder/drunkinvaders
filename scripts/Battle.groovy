import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.events.*

// This is similar to a regular SceneButton, with the exception that it can be locked/unlocked.
// To unlock it, either the "unlocked" attribute is set in the SceneEditor.
// Or, the scene that it refers to has already been completed. This logic is in the
// DrunkInvaders Producer.
class Battle extends SceneButton {

    // A scene can be unlocked, either by setting it from the SceneEditor, or by completing the level
    @Attribute
    public Boolean unlocked = false

    void activated() {
        enabled = unlocked || Game.instance.producer.isSceneUnlocked( scene )
        if (enabled) {
            actor.event("unlocked" )
        }
        super.activated()
    }

}


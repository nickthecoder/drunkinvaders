import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.events.*

class Battle extends SceneButton {

    // A scene can be unlocked, either by setting it from the SceneEditor, or by completing the level
    @Attribute
    public Boolean unlocked = false

    void activated() {
        if ( Game.instance.producer.isSceneUnlocked( scene ) ) {
            unlocked = true
        }
        if ( unlocked ) { 
            actor.event( "unlocked" )
        }
    }

    void onClicked( MouseEvent event ) {
        if (unlocked) {
            super.onClicked( event )
        } else {
            actor.event( "noEntry" ) // Make a bleep???
        }
    }

}


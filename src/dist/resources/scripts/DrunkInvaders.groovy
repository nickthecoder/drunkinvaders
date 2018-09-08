import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.collision.PixelOverlapping

class DrunkInvaders extends AbstractProducer {

    // Reset when returning to the menu
    int lives = 4

    // Reset when returning to the menu
    int score = 0

    // The overlapping strategy used within this game.
    def pixel

    void begin() {
        pixel = new PixelOverlapping(200, 128 )
    }

    boolean isSceneUnlocked( String sceneName ) {
        return preferencesRoot().node( "scenes/" + sceneName ).getBoolean( "unlocked", false )
    }

    void unlockScene( String sceneName ) {
        preferencesRoot().node( "scenes/" + sceneName ).set( "unlocked", true )
    }

}


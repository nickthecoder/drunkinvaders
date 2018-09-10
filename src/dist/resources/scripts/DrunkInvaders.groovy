import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.collision.PixelOverlapping

// There is only ever one instance of this class. Tickle creates it when the resources
// file "drunkInvaders.tickle" is loaded.
// See "Game Info" in the Tickle editor.

class DrunkInvaders extends AbstractProducer {

    // Reset when returning to the menu
    int lives = 4

    // Reset when returning to the menu
    int score = 0

    // The overlapping strategy used within this game.
    def pixel

    void begin() {
        // Note, if we have bigger objects later, then we'll need to increase the size.
        pixel = new PixelOverlapping( 200 /*size*/ , 128 /*threshold*/ )
    }

    // Stores a boolean indicating that the scene has been completed, and is therefore unlocked
    // i.e. a player can start the game at that scene.
    // The data is stored in the "Registry", so it available the next time the game is started.
    void unlockScene( String sceneName ) {
        preferencesRoot().node( "scenes/" + sceneName ).set( "unlocked", true )
    }

    // Reads the data from the registry, which may have been written by unlockScene (above).
    boolean isSceneUnlocked( String sceneName ) {
        return preferencesRoot().node( "scenes/" + sceneName ).getBoolean( "unlocked", false )
    }

}


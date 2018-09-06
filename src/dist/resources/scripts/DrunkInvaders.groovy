import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.collision.PixelOverlapping

class DrunkInvaders extends AbstractProducer {

    // The overlapping strategy used within this game.
    def pixel

    // Reset when returning to the menu
    public int lives = 4

    void begin() {
        pixel = new PixelOverlapping().threshold( 60 )
    }
}


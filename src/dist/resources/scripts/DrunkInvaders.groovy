import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.collision.PixelOverlapping

class DrunkInvaders extends AbstractProducer {

    // The overlapping strategy used within this game.
    def pixel

    def void begin() {
        pixel = new PixelOverlapping().threshold( 60 )
    }
}


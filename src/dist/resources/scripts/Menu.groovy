import uk.co.nickthecoder.tickle.*

import uk.co.nickthecoder.tickle.resources.*

// Used by the main menu.
// Extends Level, because the menu has a ship (and maybe later an alien mothership)
// So we need the newAlien and alienDied methods.
class Menu extends Level {

    void activated() {
        Game.instance.producer.lives = 4
    }

}

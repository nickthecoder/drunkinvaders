import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*

class LifeFactory extends AbstractRole {

    @Attribute
    public int spacingX = -60

    @Attribute
    public int spacingY = 0

    void activated() {
        def additionalLives = Game.instance.producer.lives -1

        for ( int i = 0; i < additionalLives; i ++ ) {
            def lifeA = actor.createChild("life")
            lifeA.x += spacingX * i
            lifeA.y += spacingY * i
            lifeA.scaleXY = actor.scaleXY
            lifeA.direction.radians = actor.direction.radians
            lifeA.role.index = i
        }
        actor.die()       
    }

    void tick() {
    }
}


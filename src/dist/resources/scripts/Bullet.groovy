import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*

class Bullet extends AbstractRole {

    public double speed = 6

    void tick() {
        actor.moveForwards( speed )

        def pixel = Game.instance.producer.pixel

        for( def enemy : actor.stage.findRolesByClass( Enemy.class ) ) {
            if (pixel.overlapping( actor, enemy.actor ) ) {
                enemy.hit()
                actor.die()
                break
            }
        }

        if ( actor.x < 0 || actor.y < 0 || actor.x > 640 || actor.y > 480 ) {
            actor.die()
        }
    }

}


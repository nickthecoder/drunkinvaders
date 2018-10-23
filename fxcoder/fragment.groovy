import javafx.application.*
import javafx.scene.image.*
import uk.co.nickthecoder.tickle.*
import uk.co.nickthecoder.tickle.resources.*
import uk.co.nickthecoder.tickle.util.*
import uk.co.nickthecoder.tickle.editor.tabs.FXCoderTab
import uk.co.nickthecoder.tickle.editor.util.ExtensionsKt
import uk.co.nickthecoder.tickle.editor.util.ImageCache

Resources.instance.textures.remove("fragments")
def managedTexture = new ManagedTexture( 300, 100 )


def makeFragments( String costumeName, ManagedTexture managedTexture ) {
    def costume = Resources.instance.costumes.find(costumeName)
    def pose = costume.choosePose("default")
    def mask = FragmentMaker.randomMaskPose( pose, 4, 10 )
    def fragmentMaker = new FragmentMaker(pose, mask)
    fragmentMaker.texture = managedTexture
    
    def fragments = fragmentMaker.generate()

    def event = costume.events["fragments"]
    if (event == null) {
        event = new CostumeEvent()
        costume.events["fragments"] = event
    }
    def count = 1
    for (fragment in fragments) {
        Resources.instance.poses.add( costumeName + "-fragment" + count, fragment.pose )
        event.poses.add( fragment.pose )
        count ++
    }
}

makeFragments( "ship", managedTexture )
makeFragments( "alien1", managedTexture )
makeFragments( "alien2", managedTexture )
makeFragments( "alien3", managedTexture )
makeFragments( "mothership", managedTexture )

def file = new File( Resources.instance.texturesDirectory, "fragments.png" )
FXCoderTab.saveImage(ExtensionsKt.toImage(managedTexture.texture), file)
managedTexture.texture.file = file
Resources.instance.textures.add( "fragments", managedTexture.texture )

//ImageCache.clear()
managedTexture.texture


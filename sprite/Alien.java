package GameMenu.sprite;
import GameMenu.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
public class Alien extends Sprite {

    public Alien(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double ox, double oy) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage, ox, oy);
    }

    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }


    }
}
package PigeonSquare;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class PigeonSprite extends Sprite
{
    PigeonSprite(double x, double y, double h)
    {
        super(new Image(PigeonSprite.class.getResourceAsStream("images/pigeon.png")), x, y, h);
    }

    public boolean isThereHuman()
    {
        return (SquareWindow.getPasserBy().getSprite().getView().getBoundsInParent().intersects(getView().getBoundsInParent()));
    }
}

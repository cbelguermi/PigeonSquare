package PigeonSquare;

import javafx.scene.image.Image;

public class HumanSprite extends Sprite
{
    HumanSprite(double x, double y, double h)
    {
        super(new Image(HumanSprite.class.getResourceAsStream("images/passerby.png")), x, y, h);
    }
}

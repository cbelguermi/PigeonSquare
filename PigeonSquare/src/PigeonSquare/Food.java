package pigeonsquare;

import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

public class Food extends Sprite implements Runnable
{
    private boolean isFresh;
    private boolean exists;

    public Food(double x, double y, double h)
    {
        super(new Image(Human.class.getResourceAsStream("images/food.png")), x, y, h);
        isFresh = true;
        exists  = true;
    }

    public boolean isFresh()
    {
        return isFresh;
    }

    public boolean exists()
    {
        return exists;
    }

    public void rottenFood()
    {
        isFresh = false;
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-1.0);

        getView().setEffect(blackout);
        getView().setCache(true);
        getView().setCacheHint(CacheHint.SPEED);
    }

    public void eat()
    {
         exists = false;
    }

    public void run()
    {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) { }

        if (exists)
        {
            rottenFood();
            System.out.println("Food has gone off");
        }
    }

    @Override
    public void translateAnimation(double translateX, double translateY) {

    }
}

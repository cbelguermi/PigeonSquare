package pigeonsquare;

import javafx.scene.CacheHint;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

public class Food extends Sprite implements Runnable
{
    private final static int FRESH_TIME = 10000;

    private boolean isFresh;
    private boolean exists;

    Food(double x, double y, double h)
    {
        super(new Image(Human.class.getResourceAsStream("images/food.png")), x, y, h);
        isFresh = true;
        exists  = true;
    }

    boolean isFresh()
    {
        return isFresh;
    }

    boolean exists()
    {
        return exists;
    }

    /**
     * Set the food to rotten, the pigeons won't eat it.
     */
    private void getRotten()
    {
        isFresh = false;
        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-1.0);

        getView().setEffect(blackout);
        getView().setCache(true);
        getView().setCacheHint(CacheHint.SPEED);
    }

    void getEaten()
    {
         exists = false;
    }

    /**
     * The thread sleeps while the food is still fresh. When FRESH_TIME is out, if the food hasn't been eaten by a
     * pigeon, it gets rotten.
     */
    public void run()
    {
        try
        {
            Thread.sleep(FRESH_TIME);
        }
        catch (InterruptedException e)
        {
            try
            {
                // Remove food from screen before terminating
                SquareController.getInstance().removeFood(this);
            }
            catch (Exception x)
            {
                System.out.println("Remove failed");
            }
        }
        if (exists)
        {
            getRotten();
            System.out.println("Food has gone off");
        }
    }

    @Override
    void translateAnimation(double translateX, double translateY) { }
} //Food

package PigeonSquare;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Pigeon extends Sprite implements Runnable
{
    /* Avoids to make pigeon move several times in a row when a human is crossing its entire sprite */
    private boolean isAfraid;

    Pigeon(double x, double y, double h)
    {
        super(new Image(Pigeon.class.getResourceAsStream("images/pigeon.png")), x, y, h);
        isAfraid = false;
    }

    //TODO: manage events with food

    public int isFoodCloseAndGood()
    {
        return (SquareController.getInstance().getClosestFreshFood(getX(), getY()));
    }

    public boolean isAfraid()
    {
        return isAfraid;
    }

    public void setAfraid(boolean bool)
    {
        isAfraid = bool;
    }

    public void flyAway()
    {
        setAfraid(true);
        translateAnimation(500, 20, 20);
        printCoordinates();
    }

    public void peckAtFood()
    {
        System.out.println("I Found Food !!");
    }

    @Override
    public void translateAnimation(int milliSec, double translateX, double translateY)
    {
        TranslateTransition translateTransition = new TranslateTransition();

        translateTransition.setDuration(Duration.millis(milliSec));

        translateTransition.setNode(getView());

        translateTransition.setByX(translateX);
        translateTransition.setByY(translateY);

        translateTransition.setCycleCount(1);

        translateTransition.setAutoReverse(false);

        translateTransition.setOnFinished(event -> {
            updatePosition();
        });

        translateTransition.play();
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (isFoodCloseAndGood() != -1)
            {
                peckAtFood();
                break;
            }
            while (isFoodCloseAndGood() == -1)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
        }
    } //run
} //Pigeon

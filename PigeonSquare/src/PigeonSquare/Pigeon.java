package pigeonsquare;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import java.util.Random;

public class Pigeon extends Sprite implements Runnable
{
    private boolean isAfraid; // Avoids to make pigeon flyAway several times in a row when human is crossing its sprite
    private final static double VELOCITY = 0.2;
    private TranslateTransition translateTransition;

    Pigeon(double x, double y, double h)
    {
        super(new Image(Pigeon.class.getResourceAsStream("images/pigeon.png")), x, y, h);
        isAfraid = false;

        this.setOnMouseClicked(me ->
        {
            if (me.getButton() == MouseButton.MIDDLE)
            {
                flyAway();
            }
        });
    }

    /**
     * Interrogates the controller to get the coordinates of eatable food.
     *
     * @return Food with valid coordinates if there is close and fresh food, null otherwise
     */
    public Food isCloseAndFreshFood()
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

    public double[] randomCoordinates()
    {
        double randPos[] = new double[2];
        Random r = new Random();
        if (getX() > SquareWindow.SCENE_WIDTH / 2)
        {
            // go to the left
            randPos[0] = - (r.nextInt(100 - 20) + 20);
        }
        else
        {
            // go to the right
            randPos[0] = r.nextInt(100 - 20) + 20;
        }
        if (getY() > SquareWindow.SCENE_HEIGHT / 2)
        {
            // go up
            randPos[1] = - (r.nextInt(100 - 20) + 20);
        }
        else
        {
            // go down
            randPos[1] = (r.nextInt(100 - 20) + 20);
        }
        return randPos;
    }

    public void flyAway()
    {
        setAfraid(true);
        double[] randPos = randomCoordinates();
        translateAnimation(randPos[0], randPos[1]);
        printCoordinates("flyAway");
        setAfraid(false);
        System.out.println(getIndex() + ": flyAway"); //TEST
    }

    public void goPeckAtFood(Food food)
    {
        translateAnimation(food.getX() - getX(), food.getY() - getY());
        System.out.println(getIndex() + ": goPeckAtFood"); //TEST
    }

    public boolean onFood(Food closestFood)
    {
        if (getX() == closestFood.getX()&& getY() == closestFood.getY())
        {
            System.out.println(getIndex() + ": onFood"); //TEST
            SquareController.getInstance().removeFood(closestFood);
            return true;
        }
        else
        return false;
    }

    public void stopMoving()
    {
        System.out.println(getIndex() + ": stopMoving"); //TEST
        translateTransition.pause();
        this.setX(this.getX());
        this.setY(this.getY());
        this.translateAnimation(0, 0);
        translateTransition.play();
    }

    @Override
    public void translateAnimation(double translateX, double translateY)
    {
        this.translateTransition = new TranslateTransition();

        translateTransition.setDuration(Duration.millis( Math.hypot(translateX, translateY) / VELOCITY));
        /*
        t = d/v
        v = 6 (pixels/milliseconds) = VELOCITY
        d = hypotenuse (translateX, translateY)
        */

        translateTransition.setNode(getView());

        translateTransition.setByX(translateX);
        translateTransition.setByY(translateY);

        translateTransition.setCycleCount(1);

        translateTransition.setAutoReverse(false);

        translateTransition.setOnFinished(event -> updatePosition());

        Platform.runLater(() -> translateTransition.play());
    }

    /**
     * Sets a pigeon's general behaviour.
     *
     * 1 Pigeon looks around if there is fresh food not too far.
     * 2 If there is,
     *      2.1 Pigeon runs towards the food
     *      2.2 While the food still exists and is still fresh and pigeon hasn't arrived on it, he does nothing but
     *          move its sprite on screen
     *      2.3 If food has been taken or got rotten or the pigeon has successfully eaten the food, its sprite stops
     *          moving
     */
    @Override
    public void run()
    {
        while (true)
        {
            Food closestFood = isCloseAndFreshFood();
            if (closestFood != null)
            {
                goPeckAtFood(closestFood);
                while (closestFood.exists() && closestFood.isFresh() && !this.onFood(closestFood))
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("got interrupted");
                        break;
                    }
                }
                stopMoving();
            }
            if (SquareController.getInstance().checkForCollision(this))
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted");
                    break;
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    } //run
} //Pigeon

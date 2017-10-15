package pigeonsquare;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
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
        translateTransition = new TranslateTransition();
        isAfraid = false;
    }

    /**
     * Interrogates the controller to get the coordinates of eatable food.
     *
     * @return Food with valid coordinates if there is close and fresh food, null otherwise
     */
     private Food isCloseAndFreshFood()
    {
        return (SquareController.getInstance().getClosestFreshFood(getX(), getY()));
    }

     boolean isAfraid()
    {
        return isAfraid;
    }

     private void setAfraid(boolean bool)
    {
        isAfraid = bool;
    }

    /**
     * Computes random coordinates in a fixed range
     * (to make the pigeon slightly move within the screen when afraid by a human).
     *
     * @return the couple of coordinates
     */
     private double[] randomCoordinates()
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

    /**
     * Makes the pigeon move a bit towards a random point.
     *
     */
     void flyAway()
    {
        setAfraid(true);
        double[] randPos = randomCoordinates();
        translateAnimation(randPos[0], randPos[1]);
        setAfraid(false);
    }

    /**
     * Sets a translation towards the given food.
     *
     * @param food to which the pigeon must go.
     */
    private void goPeckAtFood(Food food)
    {
        translateAnimation(food.getX() - getX(), food.getY() - getY());
    }

    /**
     * Checks if the pigeon is right on the given food, and if so, calls the controller to remove the food.
     *
     * @param closestFood targeted by the pigeon
     * @return true if food has been successfully removed, false if
     */
    private boolean onFood(Food closestFood)
    {
        if (getX() == closestFood.getX() && getY() == closestFood.getY())
        {
            try
            {
                SquareController.getInstance().removeFood(closestFood);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("Removal of food failed");
            }
        }
        return false;
    }

    /**
     * Makes the pigeon stop its current move.
     *
     */
    private synchronized void stopMoving()
    {
        translateTransition.pause();
        this.setX(this.getX());
        this.setY(this.getY());
        this.translateAnimation(0, 0);
        translateTransition.play();
    }

    /**
     * Plays the pigeon animation. The pigeon goes always at the same speed on screen.
     *
     * @param translateX translation on X-axis
     * @param translateY translation on Y-axis
     */
    @Override
    synchronized void translateAnimation(double translateX, double translateY)
    {
        // Duration = (distance of the translation) / speed
        translateTransition.setDuration(Duration.millis( Math.hypot(translateX, translateY) / VELOCITY));

        translateTransition.setNode(getView());

        translateTransition.setByX(translateX);
        translateTransition.setByY(translateY);

        translateTransition.setCycleCount(1);

        translateTransition.setAutoReverse(false);

        translateTransition.setOnFinished(event -> updatePosition());

        Platform.runLater(() -> translateTransition.play());
    }

    /**
     * Pigeon's behaviour:
     *
     * 1 Pigeon looks around if there is fresh food not too far.
     * 2 If there is,
     *      2.1 Pigeon runs towards the food
     *      2.2 While the food still exists and is still fresh and pigeon hasn't arrived on it, he does nothing but
     *          move its sprite on screen
     *      2.3 If food has been taken or got rotten or the pigeon has successfully eaten the food, its sprite stops
     *          moving
     * 3 If a human is near the pigeon, the pigeon flies away randomly.
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
                    } catch (InterruptedException e)
                    {
                        System.out.println("Got interrupted");
                        break;
                    }
                }
                stopMoving(); // pigeon is moving since goPeckAtFood has been called
            }
            if (SquareController.getInstance().isHumanNear(this))
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    System.out.println("got interrupted");
                    break;
                }
            }
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                System.out.println("Got interrupted");
                break;
            }
        }
    } //run
} //Pigeon

package pigeonsquare;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Pigeon extends Sprite implements Runnable
{
    /* Avoids to make pigeon move several times in a row when a human is crossing its entire sprite */
    private boolean isAfraid;
    private final static double VITESSE = 0.2;
    private TranslateTransition translateTransition;

    Pigeon(double x, double y, double h)
    {
        super(new Image(Pigeon.class.getResourceAsStream("images/pigeon.png")), x, y, h);
        isAfraid = false;

        this.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent me){
                System.out.println("Why do you hit me ??");
            }
        });
    }

    //TODO: manage events with food

    public Food isFoodCloseAndGood()
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
        translateAnimation(50, 50);
        printCoordinates();
    }

    public void peckAtFood(Food food)
    {
        System.out.println("I Found Food !!");
        this.translateAnimation(food.getX()-this.getX(), food.getY()-this.getY());
    }

    public boolean onFood(Food closestFood)
    {
        if (this.getX() == closestFood.getX() && this.getY() == closestFood.getY())
        {
            System.out.println("Miam !!");
            SquareController.getInstance().removeFood(closestFood);
            return true;
        }
        else
        return false;
    }

    public void stopMoving()
    {
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

        translateTransition.setDuration(Duration.millis( Math.hypot(translateX, translateY) / VITESSE));
        /*
        t = d/v
        v = 6 (pixels/millisecondes) = VITESSE
        d = hypot (translateX, translateY)
        */

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
            Food closestFood = isFoodCloseAndGood();
            if (closestFood != null)
            {
                peckAtFood(closestFood);
                while (closestFood.exists() && closestFood.isFresh() && !this.onFood(closestFood))
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                stopMoving();
            }

            // if human ....

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}
        }
    } //run
} //Pigeon

package pigeonsquare;

import java.util.Random;
import javafx.application.Platform;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Human extends Sprite implements Runnable
{
    private final static int WALK_TIME = 2500;

    private TranslateTransition translateTransition;
    private Random rand;

    Human(double x, double y, double h)
    {
        super(new Image(Human.class.getResourceAsStream("images/passerby.png")), x, y, h);
        rand = new Random();
    }

    /**
     * Plays the human translation. The duration is the same, so the speed of the human may change since the
     * translation values are set randomly.
     *
     * @param translateX translation on X-axis
     * @param translateY translation on Y-axis
     */
    @Override
    synchronized void translateAnimation(double translateX, double translateY)
    {
        double transX = translateX - this.getX();
        double transY = translateY - this.getY();
        
        this.translateTransition = new TranslateTransition();

        translateTransition.setDuration(Duration.millis(WALK_TIME));

        translateTransition.setNode(getView());

        translateTransition.setByX(transX);
        translateTransition.setByY(transY);

        translateTransition.setCycleCount(1);

        translateTransition.setAutoReverse(false);

        translateTransition.setOnFinished(event -> updatePosition());

        Platform.runLater(() -> translateTransition.play());
    }

    /**
     * Sets a random translation on screen within the limits of the window.
     */
    private void randomMove()
    {
        translateAnimation(rand.nextInt(SquareWindow.SCENE_WIDTH), rand.nextInt(SquareWindow.SCENE_HEIGHT));
    }

    /**
     * Moves randomly on screen, pauses and start again.
     */
    public void run()
    {
        while (true)
        {
            randomMove();
            try {
                Thread.sleep(WALK_TIME + 500);
            }
            catch (InterruptedException e)
            {
                try
                {
                    // Remove human from screen before terminating
                    SquareController.getInstance().removeHuman(this);

                }
                catch (Exception x)
                {
                    System.out.println("Remove failed");
                }
                break;
            }

        }
    } //run
} //Human
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

    public Human(double x, double y, double h)
    {
        super(new Image(Human.class.getResourceAsStream("images/passerby.png")), x, y, h);
        rand = new Random();
    }

    @Override
    public void translateAnimation(double translateX, double translateY)
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

/*
        Timeline timeline = new Timeline();
        final int microMillisDuration = 10;

        timeline.setCycleCount(10000 / microMillisDuration);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(microMillisDuration),
                event -> {
                    setX(getX() + 1);
                    setY(getY() + 1);
                    SquareController.getInstance().checkForCollision(this);
        });

        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
*/
    }

    public void randomMove()
    {
        translateAnimation(rand.nextInt(SquareWindow.SCENE_WIDTH), rand.nextInt(SquareWindow.SCENE_HEIGHT));
    }

    public void run()
    {
        while (true)
        {
            randomMove();
            try {
                Thread.sleep(WALK_TIME+500);
            }
            catch (InterruptedException e) {}
        }
    }

}

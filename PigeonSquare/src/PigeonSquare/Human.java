package pigeonsquare;

import java.util.Random;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Human extends Sprite implements Runnable
{
    private Random r;

    public Human(double x, double y, double h)
    {
        super(new Image(Human.class.getResourceAsStream("images/passerby.png")), x, y, h);
        r = new Random();
    }

    @Override
    public void translateAnimation(double translateX, double translateY)
    {
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
    }

    public void randomMove()
    {
        if (r.nextInt(100) >= 70) // move
        {
            if (r.nextInt(2) == 1)
            {
                resetPosition(0, 0);
                translateAnimation(SquareWindow.SCENE_WIDTH, SquareWindow.SCENE_HEIGHT);
            }
            else
            {
                resetPosition(SquareWindow.SCENE_WIDTH, SquareWindow.SCENE_HEIGHT);
                translateAnimation(-SquareWindow.SCENE_WIDTH, -SquareWindow.SCENE_HEIGHT);
            }
        }
    }

    public void run()
    {
        while (true)
        {
            randomMove();
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {}
        }
    }

}

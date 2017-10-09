package PigeonSquare;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

class Sprite extends Parent
{
    private ImageView imageView;

    public Sprite(Image image, double x, double y, double height)
    {
        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);

        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        this.getChildren().add(imageView);

    }

    public ImageView getView()
    {
        return this.imageView;
    }

    public double getX()
    {
        return getView().getX();
    }

    public double getY()
    {
        return getView().getY();
    }

    public void setX(double x)
    {
        getView().setX(x);
    }

    public void setY(double y)
    {
        getView().setY(y);
    }

    public void updatePosition()
    {
        setX(getX() + getView().getTranslateX());
        setY(getY() + getView().getTranslateY());
        getView().setTranslateX(0);
        getView().setTranslateY(0);
    }

    /*
    * Manages animation when moving.
     */
    public void translateAnimation(int milliSec, double translateX, double translateY)
    {
        /*
        System.out.println("Location before relocation = " + getX() + ","
                + getY() + ")");

        getTimeline().setCycleCount(1);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(milliSec),
                new KeyValue(getView().xProperty(), translateX),
                new KeyValue(getView().yProperty(), translateY));

        KeyFrame anim = new KeyFrame(Duration.millis(milliSec),
                event -> {
                    setX(getX() + translateX);
                    setY(getY() + translateY);
                    checkForCollision();
                });

        getTimeline().getKeyFrames().add(keyFrame);

       getTimeline().setOnFinished(event ->
               System.out.println("Location after relocation = " + getX() + "," + getY() + ")"));

       getTimeline().play();*/

        System.out.println("Location BEFORE relocation = " + getX() + "," + getY() + ")"); //TEST

        //Creating Translate Transition
        TranslateTransition translateTransition = new TranslateTransition();

        //Setting the duration of the transition
        translateTransition.setDuration(Duration.millis(milliSec));

        //Setting the node for the transition
        translateTransition.setNode(getView());

        //Setting the value of the transition along the x and y axis.
        translateTransition.setByX(translateX);
        translateTransition.setByY(translateY);

        //Setting the cycle count for the transition
        translateTransition.setCycleCount(1);

        //Setting auto reverse value to false
        translateTransition.setAutoReverse(false);

        translateTransition.setOnFinished(event -> {
            updatePosition();
            System.out.println("Location AFTER relocation = " + getX() + "," + getY() + ")"); //TEST
        });


        //Playing the animation
        translateTransition.play();

    }
}

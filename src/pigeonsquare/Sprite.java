package pigeonsquare;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class Sprite extends Parent
{
    private ImageView imageView;
    private int index;

    Sprite(Image image, double x, double y, double height)
    {
        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);

        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        this.getChildren().add(imageView);

        index = SquareWindow.fetchAddChildrenNumber();
    }

    int getIndex()
    {
        return index;
    }

    ImageView getView()
    {
        return this.imageView;
    }

    double getX()
    {
        return getView().getX();
    }

    double getY()
    {
        return getView().getY();
    }

    void setX(double x)
    {
        getView().setX(x);
    }

    void setY(double y)
    {
        getView().setY(y);
    }

    void updatePosition()
    {
        setX(getX() + getView().getTranslateX());
        setY(getY() + getView().getTranslateY());
        getView().setTranslateX(0);
        getView().setTranslateY(0);
    }

    /**
     * Manages animation when moving.
     */
    abstract void translateAnimation(double translateX, double translateY);


    /**
     * For test purposes.
     */
    void printCoordinates(String stamp)
    {
        System.out.println("name: " + stamp + " index: " + index + " x: " + getX() + ", y: " + getY());
    }
}

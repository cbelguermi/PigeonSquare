package pigeonsquare;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite extends Parent
{
    private ImageView imageView;
    private int index;

    public Sprite(Image image, double x, double y, double height)
    {
        this.imageView = new ImageView(image);
        this.imageView.setX(x);
        this.imageView.setY(y);

        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        this.getChildren().add(imageView);

        index = SquareWindow.fetchAddChildrenNumber();
    }

    public int getIndex()
    {
        return index;
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

    public void resetPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }

    /*
    * Manages animation when moving.
     */
    public abstract void translateAnimation(double translateX, double translateY);


    /**
     * For test purposes.
     */
    public void printCoordinates(String stamp)
    {
        System.out.println("name: " + stamp + " index: " + index + " x: " + getX() + ", y: " + getY());
    }
}

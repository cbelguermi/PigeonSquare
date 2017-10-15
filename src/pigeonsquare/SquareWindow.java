package pigeonsquare;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.input.MouseButton;

public class SquareWindow extends Application
{
    /**
     * Graphical parameters
     */
    final static int SCENE_WIDTH = 500;
    final static int SCENE_HEIGHT = 500;
    private final static int ITEM_HEIGHT = 50;

    final static String PIGEON_ELEMENT = "pigeons";
    final static String FOOD_ELEMENT = "foods";
    final static String HUMAN_ELEMENT = "humans";

    private static Group root = new Group();

    /**
     * childrenCreated: number of sprite nodes added to scene since the beginning, no matter if they are removed from
     * the scene further in the execution.
     */
    private static volatile int childrenCreated = 0;

    /**
     * Returns the current number of children sprite nodes added to the 'root' Group and then increments it.
     *
     * @return the number of children sprite nodes added to the 'root' Group before incrementation
     */
    static synchronized int fetchAddChildrenNumber()
    {
        int ret = childrenCreated;
        childrenCreated += 1;
        return ret;
    }

    /**
     * Removes sprite from scene.
     *
     * @param sprite sprite to be deleted
     */
    static synchronized void deleteSprite(Sprite sprite)
    {
        System.out.println(getRoot().getChildren());
        Platform.runLater(() -> getRoot().getChildren().remove(sprite));
    }

    /**
     * Get the group containing all sprite nodes from the scene.
     *
     * @return the root node group
     */
    static Group getRoot()
    {
        return root;
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pigeon Square");
        Scene scene = new Scene(getRoot(), SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        /* Main user interaction */
        scene.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY)
            {
                try {
                    if (!SquareController.getInstance().addElement(PIGEON_ELEMENT,
                            me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT))
                    {
                        System.out.println("Adding pigeon failed");
                    }
                    System.out.println(getRoot().getChildren());

                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Bad item type");
                }
            }
            else if  (me.getButton() == MouseButton.SECONDARY)
            {
                try {
                    if (!SquareController.getInstance().addElement(FOOD_ELEMENT,
                            me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT))
                    {
                        System.out.println("Adding food failed");
                    }
                    System.out.println(getRoot().getChildren());

                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Bad item type");
                }
            }
            else if  (me.getButton() == MouseButton.MIDDLE)
            {
                try {
                    if (!SquareController.getInstance().addElement(HUMAN_ELEMENT,
                            me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT))
                    {
                        System.out.println("Adding human failed");
                    }
                    System.out.println(getRoot().getChildren());
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("Bad item type");
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}

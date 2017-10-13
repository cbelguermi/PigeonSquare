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
    public final static int SCENE_WIDTH = 500;
    public final static int SCENE_HEIGHT = 500;
    public final static int ITEM_HEIGHT = 50;

    private final String pigeonElement = "pigeons";
    private final String foodElement = "foods";

    /**
     * Graphical objects
     */
    private static Group root;
    private static Scene scene;

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
    public static synchronized int fetchAddChildrenNumber()
    {
        int ret = childrenCreated;
        childrenCreated += 1; //changed childrenCreated++ to childrenCreated+=1
        System.out.println("childrenCreated +1: " + childrenCreated);
        return ret;
    }

    /**
     * Removes food sprite from scene.
     *
     * @param food eaten food to be deleted
     */
    public static synchronized void deleteFood(Food food)
    {
        Platform.runLater(() -> {
            System.out.println(root.getChildren());
            System.out.println(food.getIndex() + ": food deleted !!");
            root.getChildren().remove(food);
        });
    }

    /**
     * Get the group containing all sprite nodes from the scene.
     *
     * @return the root node group
     */
    public static Group getRoot()
    {
        return root;
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pigeon Square");
        root = new Group();
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        /* Main user interaction */
        scene.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY)
            {
                System.out.println("Send a new pigeon!!"); //TEST
                try {
                    SquareController.getInstance().addElement(pigeonElement,
                            me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if  (me.getButton() == MouseButton.SECONDARY)
            {
                System.out.println("Send food!!"); //TEST
                try {
                    SquareController.getInstance().addElement(foodElement,
                            me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT);
                } catch (Exception e) {
                    e.printStackTrace();
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

package pigeonsquare;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

public class SquareWindow extends Application
{
    public final static int SCENE_WIDTH = 500;
    public final static int SCENE_HEIGHT = 500;
    public final static int ITEM_HEIGHT = 50;

    private static Group root;
    private static Scene scene;

    public static void deleteChild ()
    {
        root.getChildren().remove(0,1);
    }

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
        scene.setOnMouseClicked(new EventHandler<MouseEvent>(){

            public void handle(MouseEvent me){
                if (me.getButton() == MouseButton.PRIMARY)
                {
                    System.out.println("Send a new pigeon !!");
                    Pigeon pigeon = new Pigeon(me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2),ITEM_HEIGHT);
                    SquareController.getInstance().addPigeon(pigeon);

                }
                else if  (me.getButton() == MouseButton.SECONDARY)
                {
                    System.out.println("Send food !!");
                    Food food = new Food(me.getSceneX()-(ITEM_HEIGHT/2), me.getSceneY()-(ITEM_HEIGHT/2), ITEM_HEIGHT);
                    SquareController.getInstance().addFood(food);
                }
                else if  (me.getButton() == MouseButton.MIDDLE)
                {
                    System.out.println("Try to delete" + root.getChildren());
                    deleteChild();
                }

            }
        });
        /* Set elements on scene */
        //Human passerBy = new Human(0, 0, 50);
        //SquareController.getInstance().addHuman(passerBy);
        //root.getChildren().add(passerBy);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}

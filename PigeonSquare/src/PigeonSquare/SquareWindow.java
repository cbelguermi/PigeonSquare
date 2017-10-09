package PigeonSquare;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SquareWindow extends Application
{
    public final static int SCENE_WIDTH = 1000;
    public final static int SCENE_HEIGHT = 1000;

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pigeon Square");
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        /* Set elements on scene */
        Human passerBy = new Human(0, 0, 50);
        SquareController.getInstance().addHuman(passerBy);
        root.getChildren().add(passerBy);
        Pigeon pigeon1 = new Pigeon(50, 50, 50);
        Pigeon pigeon2 = new Pigeon(400, 400, 50);
        SquareController.getInstance().addPigeon(pigeon1);
        SquareController.getInstance().addPigeon(pigeon2);
        root.getChildren().add(pigeon1);
        root.getChildren().add(pigeon2);

        Food food01 = new Food(8,9, 50);
        SquareController.getInstance().addFood(food01);
        root.getChildren().add(food01);

        Food food02 = new Food(15,500, 50);
        SquareController.getInstance().addFood(food02);
        root.getChildren().add(food02);

        SquareController.getInstance().printFoods();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
package PigeonSquare;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SquareWindow extends Application
{
    private static int SCENE_WIDTH = 1000;
    private static int SCENE_HEIGHT = 1000;

    private static ArrayList<Pigeon> pigeons;
    private static Human passerBy;

    public static Human getPasserBy() {
        return passerBy;
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Pigeon Square");
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        passerBy = new Human(-10, -10, 50);
        root.getChildren().add(passerBy.getSprite());

        pigeons = new ArrayList<>();
        pigeons.add(new Pigeon(50, 50, 50));
        pigeons.add(new Pigeon(400, 400, 50));

        for (Pigeon pigeon : pigeons)
        {
            root.getChildren().add(pigeon.getSprite());
            pigeon.start();
        }

        passerBy.start();

        primaryStage.setScene(scene);
        primaryStage.show();

        Food food01 = new Food(8,9);
        System.out.println("Food01 created");
        Square.getInstance().addFood(food01);
        System.out.println("Food01 added");
        Square.getInstance().printFood();

        Food food02 = new Food(10,10);
        Square.getInstance().addFood(food02);
        System.out.println("Food02 added");
        Square.getInstance().printFood();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
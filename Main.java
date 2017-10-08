package pigeon;

public class Main
{
    public static void main(String[] args)
    {
        Pigeon pigeon01 = new Pigeon(5,8);
        Pigeon pigeon02 = new Pigeon(22,18);
        System.out.println("Pigeons created");
        pigeon01.start();
        System.out.println("Pigeon01 launched");
        pigeon02.start();
        System.out.println("Pigeon02 launched");

        Square.getInstance().printFood();

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

}

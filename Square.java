package pigeon;

public class Square
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
    }
}

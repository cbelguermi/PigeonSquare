package PigeonSquare;

public class Pigeon extends Thread
{
    private int posX;
    private int posY;

    private PigeonSprite sprite;

    public Pigeon(int x, int y, int h)
    {
        posX = x;
        posY = y;
        sprite = new PigeonSprite(x, y, h);
    }

    public PigeonSprite getSprite()
    {
        return sprite;
    }

    @Override
    public void run()
    {
        int n = 0;
        while (n++ < 10)
        {
            while (!(getSprite().isThereHuman()) && Square.getInstance().getClosestFood(posX, posY) == -1)
            {
                System.out.println("in the safe while: " + getSprite().isThereHuman());
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
            if (getSprite().isThereHuman() == true)
            {
                System.out.println("coucou!!!!");
                getSprite().translateAnimation(2000, 200, 200);
            }
            if (Square.getInstance().getClosestFood(posX, posY) != -1)
            {
                System.out.println("I Found Food !!");
                break;
            }
        }
    } //run
} //Pigeon

package pigeon;

public class Pigeon extends Thread
{
    int posx;
    int posy;

    public Pigeon(int x, int y)
    {
        posx = x;
        posy = y;
    }

    public void run()
    {
        while (true)
        {
            while (/*pas d'humain à coté &&*/  Square.getInstance().getClosestFood(posx, posy) == -1)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
            /*if (humain à coté)
                se déplacer*/
            if (Square.getInstance().getClosestFood(posx, posy) != -1)
            {
                System.out.println("I Found Food !!");
                break;
            }
        }
    }
}

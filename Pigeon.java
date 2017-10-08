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
            //while (/*pas d'humain à coté &&*/  pas de nourriture à coté)
            //{
                try {
                    if (Square.getInstance().getClosestFood(posx, posy) != -1)
                    {
                        System.out.println("I Found Food !!");
                        break;
                    }
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {}
            //}
            /*if (humain à coté)
                se déplacer
            if (nourriture)
                se déplacer*/
        }
    }
}

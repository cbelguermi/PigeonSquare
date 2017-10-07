package pigeon;

public class Food extends Thread
{
    int posx;
    int posy;
    boolean healthy;

    public Food(int x, int y)
    {
        posx = x;
        posy = y;
        healthy = true;
    }

    public void run()
    {
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {}

        healthy = false;
    }
}

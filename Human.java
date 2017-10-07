package pigeon;

public class Human extends Thread
{
    int posx;
    int posy;

    public Human(int x, int y)
    {
        posx = x;
        posy = y;
    }

    public void run()
    {
        while (true)
        {
            posx++;
            try {
                Thread.sleep(2);
            }
            catch (InterruptedException e) {}

            posy+=2;
            try {
                Thread.sleep(2);
            }
            catch (InterruptedException e) {}
        }
    }

}

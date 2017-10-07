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
        int n=0;
        while (n++ < 10) {
            System.out.println(this.posx + " " + this.posy);
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {}
       }
    }
}

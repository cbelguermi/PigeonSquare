package PigeonSquare;

import java.util.Random;

public class Human extends Thread
{
    private int posx;
    private int posy;

    private HumanSprite sprite;

    public Human(int x, int y, int h)
    {
        posx = x;
        posy = y;
        sprite = new HumanSprite(x, y, h);
    }

    public HumanSprite getSprite()
    {
        return sprite;
    }

    public void run()
    {
        Random r = new Random();
        while (true)
        {
            getSprite().translateAnimation(2000, 20, 20);
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

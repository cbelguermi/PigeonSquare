package pigeon;

public class Square
{
    private static final int TOO_FAR = 999999999;
    private static final int MAXFOOD = 10;
    private Food foodTab[];

    private Square ()
    {
        foodTab = new Food[MAXFOOD];
    }

    private static Square SQUARE = new Square();

    public static Square getInstance()
    {
        return SQUARE;
    }

    public boolean addFood(Food food)
    {
        boolean done = false;
        int i = 0;
        while (done == false && i < 10)
        {
            if (this.foodTab[i] == null)
            {
                foodTab[i] = food;
                food.start();
                done = true;
            }
            i++;
        }
        return done;
    }

    public void printFood()
    {
        for (int i=0; i<MAXFOOD; i++)
        {
            if (this.foodTab[i] != null)
            {
                System.out.println(this.foodTab[i].posx + "-" + this.foodTab[i].posy + " : " + this.foodTab[i].healthy);
            }
        }
    }

    public int getClosestFood(int x, int y)
    {
        double distance = TOO_FAR;
        for (int i=0; i<MAXFOOD; i++)
        {
            if (this.foodTab[i] != null && this.foodTab[i].healthy)
            {
                // pythagore a = sqrt((b*b) + (c*c))
                double newDist = Math.sqrt( Math.pow(this.foodTab[i].posx - x ,2) +  Math.pow(this.foodTab[i].posy - y ,2) ) ;
                distance = Math.min (newDist, distance);
            }
        }
        return (distance == TOO_FAR) ? -1 : (int)distance;
    }

}

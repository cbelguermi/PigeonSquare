package pigeonsquare;

public class SquareController
{
    private static final int TOO_FAR = SquareWindow.SCENE_HEIGHT;
    private static final int MAX_FOODS = 10;
    private static final int MAX_HUMANS = 10;
    private static final int MAX_PIGEONS = 10;

    private Food[] foods;
    private Pigeon[] pigeons;
    private Human[] humans;

    private SquareController()
    {
        foods = new Food[MAX_FOODS];
        pigeons = new Pigeon[MAX_PIGEONS];
        humans = new Human[MAX_HUMANS];
    }

    private static SquareController SQUARECONTROLLER = new SquareController();

    public static SquareController getInstance()
    {
        return SQUARECONTROLLER;
    }

    //TODO: remove these disgusting inter-duplicated methods

    public boolean addHuman(Human human)
    {
        boolean done = false;
        int i = 0;
        while (done == false && i < MAX_HUMANS)
        {
            if (humans[i] == null)
            {
                humans[i] = human;
                Thread threadHuman = new Thread(human);
                threadHuman.start();
                done = true;
            }
            i++;
        }
        return done;
    }

    public boolean addPigeon(Pigeon pigeon)
    {
        boolean done = false;
        int i = 0;
        while (done == false && i < MAX_PIGEONS)
        {
            if (pigeons[i] == null)
            {
                pigeons[i] = pigeon;
                Thread threadPigeon = new Thread(pigeon);
                threadPigeon.start();
                SquareWindow.getRoot().getChildren().add(pigeon);
                done = true;
            }
            i++;
        }
        if (i >= MAX_PIGEONS)
        {
            System.out.println("Stop adding pigeons !!");
        }
        return done;
    }

    public boolean addFood(Food food)
    {
        boolean done = false;
        int i = 0;
        while (done == false && i < MAX_FOODS)
        {
            if (foods[i] == null)
            {
                foods[i] = food;
                Thread threadFood = new Thread(food);
                threadFood.start();
                SquareWindow.getRoot().getChildren().add(food);
                done = true;
            }
            i++;
        }
        if (i >= MAX_FOODS)
        {
            System.out.println("Stop playing with food !!");
        }
        return done;
    }

    public void removeFood(Food food)
    {
        boolean done = false;
        int i = 0;
        while (i < MAX_FOODS && done == false)
        {
            if (foods[i] != null && foods[i].getIndex() == food.getIndex())
            {
                foods[i].eat();
                foods[i] = null;
                SquareWindow.deleteFood(food.getIndex());
                done = true;
            }
            i++;
        }
    }

    public void printFoods()
    {
        for (int i = 0; i < MAX_FOODS; i++)
        {
            if (this.foods[i] != null)
            {
                System.out.println(this.foods[i].getX() + "-" + this.foods[i].getY() + " : " + this.foods[i].isFresh());
            }
        }
    }

    public Food getClosestFreshFood(double x, double y)
    {
        double distance = TOO_FAR;
        Food closestFood = null;
        for (int i = 0; i < MAX_FOODS; i++)
        {
            if (this.foods[i] != null && foods[i].isFresh())
            {
                // Pythagore a = sqrt((b*b) + (c*c))
                // double newDist = Math.sqrt(Math.pow(foods[i].getX() - x, 2) + Math.pow(foods[i].getY() - y, 2));
                double newDist = Math.hypot(foods[i].getX() - x, foods[i].getY() - y);
                if (newDist < distance)
                {
                    distance = newDist;
                    closestFood = foods[i];
                }
            }
        }
        return (distance == TOO_FAR) ? null : closestFood;
    }

    public void checkForCollision(Human human)
    {
        for (Pigeon pigeon : pigeons)
        {
            if (pigeon != null)
            {
                if (human.getView().getBoundsInParent().intersects(pigeon.getView().getBoundsInParent()) && !pigeon.isAfraid())
                {
                    pigeon.flyAway();
                }
            }
        }
    }

}

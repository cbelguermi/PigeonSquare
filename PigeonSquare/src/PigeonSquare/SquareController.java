package pigeonsquare;

import java.util.concurrent.CopyOnWriteArrayList;

public class SquareController
{
    private static final int TOO_FAR = (SquareWindow.SCENE_HEIGHT / 2);
    private static final int MAX = 10;
    //private static final int MAX_HUMANS = 10;

    /**
     * foods: array of Food items currently running and on screen.
     * pigeons: array of Pigeon items currently running and on screen.
     */
    private static CopyOnWriteArrayList<Food> foods;
    private static CopyOnWriteArrayList<Pigeon> pigeons;
    //private Human[] humans;

    private SquareController()
    {
        foods = new CopyOnWriteArrayList<>();
        pigeons = new CopyOnWriteArrayList<>();
        //humans = new Human[MAX_HUMANS];
    }

    private static SquareController SQUARECONTROLLER = new SquareController();

    public static SquareController getInstance()
    {
        return SQUARECONTROLLER;
    }

    private CopyOnWriteArrayList getArrayList(String type)
    {
        if (type.equals("pigeons"))
        {
            return pigeons;
        }
        else if (type.equals("foods"))
        {
            return foods;
        }
        return null;
    }

    /**
     * Creates a new thread responsible corresponding to one particular item on screen that has been added by the user
     * (Pigeon or Food).
     *
     * @param elementOf Sprite type to be added and be bound to a thread
     * @return true if element has been successfully added, false otherwise
     * @throws Exception if element isn't from a proper type (Pigeon or Food)
     */
    public boolean addElement(String elementOf, double x, double y, double h) throws Exception
    {
        if (!elementOf.equals("pigeons") && !elementOf.equals("foods"))
        {
            throw new Exception("Element type don't match correct item types");
        }
        boolean done = false;
        if (getArrayList(elementOf).size() < MAX)
        {
            Runnable element;
            if (elementOf.equals("pigeons"))
            {
                element = new Pigeon(x, y, h);
            }
            else
            {
                element = new Food(x, y, h);
            }
            getArrayList(elementOf).add(element);
            Thread thread = new Thread(element);
            thread.start();
            SquareWindow.getRoot().getChildren().add((Sprite) element);
            ((Sprite) element).printCoordinates(elementOf); //TEST
            done = true;
        }
        else
        {
            System.out.println("Stop flooding with sprites!!"); //TEST
        }
        return done;
    }

    /**
     * Updates state of food element eaten by a pigeon and removes the food from the list of foods.
     *
     * @param food eaten by a pigeon, to be removed from list
     * @return true if food has been successfully updated and removed, false otherwise
     */
    public synchronized boolean removeFood(Food food)
    {
        boolean done = false;
        for (Food foodItem : foods)
        {
            if ((food!= null) && foodItem.getIndex() == food.getIndex())
            {
                food.printCoordinates("foodToBeEaten"); //TEST
                food.getEaten();
                SquareWindow.deleteFood(food);
                foods.remove(foods.indexOf(foodItem));
                //food = null;
                done = true;
            }
        }
        return done;
    }

    public synchronized Food getClosestFreshFood(double x, double y)
    {
        double distance = TOO_FAR;
        Food closestFood = null;
        for (Food foodItem : foods)
        {
            if (foodItem.isFresh())
            {
                double newDist = Math.hypot(foodItem.getX() - x, foodItem.getY() - y);
                if (newDist < distance) {
                    distance = newDist;
                    closestFood = foodItem;
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

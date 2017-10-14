package pigeonsquare;

import java.util.concurrent.CopyOnWriteArrayList;

public class SquareController
{
    private static final int TOO_FAR = SquareWindow.SCENE_HEIGHT + SquareWindow.SCENE_WIDTH;
    private static final int MAX_PIGEONS = 10;
    private static final int MAX_FOOD = 10;
    private static final int MAX_HUMANS = 3;

    /**
     * foods: array of Food items currently running and on screen.
     * pigeons: array of Pigeon items currently running and on screen.
     */
    private static CopyOnWriteArrayList<Food> foods;
    private static CopyOnWriteArrayList<Pigeon> pigeons;
    private static CopyOnWriteArrayList<Human> humans;
    //private Human[] humans;

    private SquareController()
    {
        foods = new CopyOnWriteArrayList<>();
        pigeons = new CopyOnWriteArrayList<>();
        humans = new CopyOnWriteArrayList<>();
    }

    private static SquareController SQUARECONTROLLER = new SquareController();

    public static SquareController getInstance()
    {
        return SQUARECONTROLLER;
    }

    private CopyOnWriteArrayList<Pigeon> getPigeonsList()
    {
        return pigeons;
    }

    private CopyOnWriteArrayList<Food> getFoodList()
    {
        return foods;
    }

    private CopyOnWriteArrayList<Human> getHumanList()
    {
        return humans;
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
        Runnable element;
        if (elementOf.equals(SquareWindow.PIGEON_ELEMENT))
        {
            if (getPigeonsList().size() < MAX_PIGEONS)
            {
                element = new Pigeon(x, y, h);
                getPigeonsList().add((Pigeon) element);
                Thread thread = new Thread(element);
                thread.start();
                SquareWindow.getRoot().getChildren().add((Sprite) element);
                ((Sprite) element).printCoordinates(elementOf); //TEST
                return true;
            }
            else
            {
                System.out.println("Stop flooding with pigeons !!"); //TEST
            }
        }
        else if (elementOf.equals(SquareWindow.FOOD_ELEMENT))
        {
            if (getFoodList().size() < MAX_FOOD)
            {
                element = new Food(x, y, h);
                getFoodList().add((Food) element);
                Thread thread = new Thread(element);
                thread.start();
                SquareWindow.getRoot().getChildren().add((Sprite) element);
                ((Sprite) element).printCoordinates(elementOf); //TEST
                return true;
            }
            else
            {
                System.out.println("Stop flooding with food !!"); //TEST
            }
        }
        else if (elementOf.equals(SquareWindow.HUMAN_ELEMENT))
        {
            if (getHumanList().size() < MAX_HUMANS)
            {
                element = new Human(x, y, h);
                getHumanList().add((Human) element);
                Thread thread = new Thread(element);
                thread.start();
                SquareWindow.getRoot().getChildren().add((Sprite) element);
                ((Sprite) element).printCoordinates(elementOf); //TEST
                return true;
            }
            else
            {
                System.out.println("Stop flooding with humans !!"); //TEST
            }
        }
        else
        {
            throw new Exception("Element type don't match correct item types");
        }
        return false;
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

    public boolean checkForCollision(Pigeon pigeon)
    {
        for (Human human : humans)
        {
            if (human != null)
            {
                if (pigeon.getView().getBoundsInParent().intersects(human.getView().getBoundsInParent()) && !pigeon.isAfraid())
                {
                    pigeon.flyAway();
                    return true;
                }
            }
        }
        return false;
    }

}

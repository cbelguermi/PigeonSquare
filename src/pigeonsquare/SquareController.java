package pigeonsquare;

import java.util.concurrent.CopyOnWriteArrayList;

class SquareController
{
    /**
     * TOO_FAR: defines the distance over which pigeons don't go to food
     * MAX_* values limit the number of simultaneous items per type on screen.
     */
    private static final int TOO_FAR = SquareWindow.SCENE_HEIGHT + SquareWindow.SCENE_WIDTH;
    private static final int MAX_PIGEONS = 10;
    private static final int MAX_FOOD = 10;
    private static final int MAX_HUMANS = 3;

    /**
     * foods: array of Food items currently running and on screen.
     * pigeons: array of Pigeon items currently running and on screen.
     * humans: array of Human items currently running and on screen.
     */
    private static CopyOnWriteArrayList<Food> foods;
    private static CopyOnWriteArrayList<Pigeon> pigeons;
    private static CopyOnWriteArrayList<Human> humans;

    private SquareController()
    {
        foods = new CopyOnWriteArrayList<>();
        pigeons = new CopyOnWriteArrayList<>();
        humans = new CopyOnWriteArrayList<>();
    }

    /**
     * Singleton pattern
     */
    private static SquareController SQUARECONTROLLER = new SquareController();
    static SquareController getInstance()
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
     * @throws IllegalArgumentException if element isn't from a proper type (Pigeon or Food)
     */
    boolean addElement(String elementOf, double x, double y, double h) throws IllegalArgumentException
    {
        Runnable element;
        switch (elementOf)
        {
            case SquareWindow.PIGEON_ELEMENT:
                if (getPigeonsList().size() < MAX_PIGEONS)
                {
                    element = new Pigeon(x, y, h);
                    if (!getPigeonsList().add((Pigeon) element))
                    {
                        return false;
                    }
                    Thread thread = new Thread(element);
                    thread.start();
                    SquareWindow.getRoot().getChildren().add((Sprite) element);
                    ((Sprite) element).printCoordinates(elementOf); //TEST
                    return true;
                }
                else {
                    System.out.println("Stop flooding with pigeons !!"); //TEST
                }
                break;
            case SquareWindow.FOOD_ELEMENT:
                if (getFoodList().size() < MAX_FOOD)
                {
                    element = new Food(x, y, h);
                    if (!getFoodList().add((Food) element))
                    {
                        return false;
                    }
                    Thread thread = new Thread(element);
                    thread.start();
                    SquareWindow.getRoot().getChildren().add((Sprite) element);
                    return true;
                }
                else {
                    System.out.println("Stop flooding with food !!");
                }
                break;
            case SquareWindow.HUMAN_ELEMENT:
                if (getHumanList().size() < MAX_HUMANS)
                {
                    element = new Human(x, y, h);
                    if (!getHumanList().add((Human) element))
                    {
                        return false;
                    }
                    Thread thread = new Thread(element);
                    thread.start();
                    SquareWindow.getRoot().getChildren().add((Sprite) element);
                    return true;
                }
                else {
                    System.out.println("Stop flooding with humans !!");
                }
                break;
            default:
                throw new IllegalArgumentException("Element type don't match correct item types");
        }
        return false;
    }

    /**
     * Updates state of food element eaten by a pigeon and removes the food from the list of foods.
     *
     * @param food eaten by a pigeon, to be removed from list
     * @exception Exception if remove returned false.
     */
    synchronized void removeFood(Food food) throws Exception
    {
        for (Food foodItem : foods)
        {
            if ((food!= null) && foodItem.getIndex() == food.getIndex())
            {
                food.getEaten();
                SquareWindow.deleteSprite(food);
                if (!foods.remove(foodItem))
                {
                    throw new Exception("Remove failed");
                }
            }
        }
    }

    /**
     * Removes the human from the list of humans.
     *
     * @implNote This method is only used in the case of a thread representing a human being interrupted.
     *
     * @param human to be removed from list
     * @exception Exception if remove returned false.
     */
    synchronized void removeHuman(Human human) throws Exception
    {
        for (Human humanItem : humans)
        {
            if ((human!= null) && humanItem.getIndex() == human.getIndex())
            {
                SquareWindow.deleteSprite(human);
                if (!humans.remove(humanItem))
                {
                    throw new Exception("Remove failed");
                }
            }
        }
    }

    /**
     * Finds the closest and not too far fresh food from the given coordinates (x, y) of the pigeon.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return null if no food met the conditions, the closest found food otherwise
     */
    synchronized Food getClosestFreshFood(double x, double y)
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

    /**
     * Checks if a human walks near the given pigeon.
     *
     * @param pigeon on screen
     * @return true if a human intersects the pigeon's sprite, false otherwise
     */
    boolean isHumanNear(Pigeon pigeon)
    {
        for (Human human : humans)
        {
            if (human != null)
            {
                if (pigeon.getView().getBoundsInParent().intersects(human.getView().getBoundsInParent())
                        && !pigeon.isAfraid())
                {
                    pigeon.flyAway();
                    return true;
                }
            }
        }
        return false;
    } //isHumanNear
} //SquareController

import java.util.ArrayList;

/**
 * This class is part of the "Eaten by a Dolphin" application. 
 * "Eaten by a Dolphin" is a highly interesting, text based adventure game.
 * 
 * An item object stores its description, weight, and the status whether it is currently
 * being carried by the user.
 * 
 * @ SeoHyun Jen Kwon (k21070195)
 * @ 2021.11.
 */
public class Item
{
    private String itemName;
    private String itemDescription;
    private int weight;             // 0 if it can't be carried by the player
    private boolean isCarried;
    private Room initialLocation;        // initial room where it could be found
                                        // null if it belongs to a character
                                        
    /**
     * Constructor for objects of class Item
     */
    public Item(String anItemName, String anItemDescription, int aWeight, Room anInitialLocation)
    {
        itemName = anItemName;
        itemDescription = anItemDescription;
        weight = aWeight;
        isCarried = false;
        initialLocation = anInitialLocation;
    }

    /*
    /**
     * Define the name of the item.
     * 
     * @parm anItemName The name of the item.
     */
    /*
    public void setItemName(String anItemName)
    {
        itemName = anItemName;
    }
    */
    
    
    /**
     * Return the name of the item.
     * 
     * @return The name of the item.
     */
    public String getItemName()
    {
        return itemName;
    }
    
    /*
    /**
     * Define the description of the item.
     * The description could imply the usage of it, but not in explicit way.
     *
     * @param  anItemDescription The description of the item.
     */
    /*
    public void setItemDescription(String anItemDescription)
    {
        itemDescription = anItemDescription;
    }
    */
    
    /**
     * Return the description of the item.
     * 
     * @return The description of the item.
     */
    public String getItemDescription()
    {
        return itemDescription;
    }
    
    /*
    /**
     * Define the weight of the item.
     * If the weight changes to 0, it becomes uncarriable.
     * 
     * @parm aWeight The weight of the item.
     */
    /*
    public void setWeight(int aWeight)
    {
        weight = aWeight;
    }
    */
    
    /**
     * Return the weight of the item.
     * 
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Switch the status whether the item is being carried with the user.
     */
    public void switchIsCarried()
    {
        isCarried = !isCarried;
    }
    
    /**
     * Get the status whether the item is currently being carreid by the user.
     * 
     * @return The status of item whether it's being carried by the user.
     */
    public boolean getIsCarried()
    {
        return isCarried;
    }
    
    /**
     * Define the initial location of the item.
     * 
     * @parm anInitialLocation
     */
    public void setInitialRoom(Room anInitialLocation)
    {
        initialLocation = anInitialLocation;
    }
    
    /**
     * Return the initial location of the item.
     * 
     * @return The room the item initally located.
     */
    public Room getInitialRoom()
    {
        return initialLocation;
    }
    
    /**
     * Return the information of item in the form of:
     *      "NAME (DESCRIPTION) WEIGHT grams"
     *   or "NAME (DESCRIPTION) You can't pick up this item." if the item can't be carried.
     * 
     * @return The String information of item object.
     */
    public String getItemInformation()
    {
        String returnString = "\n" + itemName + "   (" + itemDescription + ")   ";
        if(weight>0)
        {
            returnString += weight + " gram(s) )";
        }
        else
        {
            returnString += "    You can't pick up this item.";
        }
        
        return returnString;
    }
}    

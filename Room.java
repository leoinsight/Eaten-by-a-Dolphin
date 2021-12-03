import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Eaten by a Dolphin" application. 
 * "Eaten by a Dolphin" is a highly interesting, text based adventure game. 
 * 
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * There are some information to display for the user when he needs help.
 * 
 * There is a list of items in some rooms. Some items can be picked up by the user, but the user
 * can't carry more than 1,000 grams of items in total.
 * 
 * @author  Michael Kölling, David J. Barnes, and SeoHyun Jen Kwon (k21070195)
 * @version 2021.11.
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private String helpInformation;         // store information to provide when the user's command is "help".
    //private ArrayList<Item> itemList;     // store items in this room.
    //private Item missionItem;                   // stores item that needs to be used to accomplish the mission in this room.
                                                // some rooms have a mission to complete, while some don't.
    private String missionCompleteMessage;         // stores the message to display when the player cleared the mission of this room                                    
    private boolean isMagicTransporter;                                  
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        helpInformation = "No information of help in this room.";
        //itemList = new ArrayList<>();
        //missionItem = null;
        missionCompleteMessage = "No message for mission completion in this room.";
        isMagicTransporter = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The name of the room
     * (the one that was defined in the constructor).
     */
    public String getRoomName()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are in the " + description + " of the dolphin.\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        //System.out.println("direction  " + direction + " exit   " + exits.get(direction));
        return exits.get(direction);
    }
    
    /**
     * Define an information to provide when the user needs help in the room.
     * 
     * @parm aHelpInformation The information to help the user in the room.
     */
    public void setHelpInformation(String aHelpInformation)
    {
        helpInformation = aHelpInformation;
    }
    
    /**
     * Return the information to provide when the user types in "help".
     * 
     * @return The information for help.
     */
    public String getHelpInformation()
    {
        return helpInformation;
    }
    
    
    /**
     * Add a created item in the list of the items in the room.
     * 
     * @parm anItemName The name of the item to add.
     * @parm anItemDescription The description of the item to add.
     * @parm aWeight The weight of the item.
     */
    /*
    public void addItem(Item anItem)
    {
        itemList.add(anItem);
    }
    */
    
    
    /**
     * Set the mission for some rooms. A item choesen among the items in a certain room
     * is needed to complete the mission.
     * 
     * @parm missionItemName The name of the item that needs to be used to complete the mission.
     */
    /*
    public void setMissionItem(Item missionItem)
    {
        this.missionItem = missionItem;
    }
    */

    
    /**
     * Return the item needed to complete the mission in this room, if there's any.
     * If there's no mission to complete, return null
     * 
     * @return The item needed to complete the mission in this room.
     */
    /*
    public Item getMissionItem()
    {
        return missionItem;
    }
    */
    
    /** Set the message to display when the mission is completed.
     * It explains what became available.
     * 
     * @parm missonCompleteMessage The message to display when the mission is completed.
     */
    public void setMissionCompleteMessage(String aMissionCompleteMessage)
    {
        missionCompleteMessage = aMissionCompleteMessage;
    }
    
    /**
     * Return the message to display when the mission is completed.
     * It explains what became available.
     * 
     * @return The message to display when the mission is cleared.
     */
    public String getMissionCompleteMessage()
    {
        return "## Mission cleared in the " + this.getRoomName() + ". ##" + "\n" + missionCompleteMessage;
    }
    
    /**
     * Switch whether the room is a magic transporter room.
     * The player moves to a random room when entering the magic transporter room.
     */
    public void setIsMagicTransporter()
    {
        isMagicTransporter = !isMagicTransporter;
    }
    
    /**
     * Return whether the room is a magic transporter room.
     * 
     * @return Whether the room is a magic transporter room.
     */
    public boolean getIsMagicTransporter()
    {
        return isMagicTransporter;
    }
    
    /*
    /**
     * Return the list of the items in the room.
     * 
     * @return The list of itmes in the room.
     */
    /*
    public ArrayList<Item> getItemList()
    {
        return itemList;
    }
    */
    
    
    /*
    /**
     * Return the item with the name given from the item list.
     * 
     * @parm itemName The name of the item to return.
     */
    /*
    public Item getItem(String itemName)
    {
        for(Item item: itemList)
        {
            if(item.getItemName().equals(itemName))
            {
                return item;
            }
        }
        return null;
    }
    */
}


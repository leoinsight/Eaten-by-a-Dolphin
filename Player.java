import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class is part of the "Eaten by a Dolphin" application. 
 * "Eaten by a Dolphin" is a highly interesting, text based adventure game.
 * 
 * A player object stores the current room he is in, the list of rooms he has been, the list of items he picked up,
 * the total weight of item he picked up, the map recording whether he completed mission in each room,
 * and whether he won the character.
 * 
 * 
 * @ SeoHyun Jen Kwon (k21070195)
 * @ 2021.11.
 */
public class Player
{
    private Room currentRoom;
    private ArrayList<Room> lastRoomList;      // room to go if the player commands back
    private ArrayList<Item> pickedUpItemList;       // the list of the items currently carried by the user
    private int totalWeightPickedUp;
    private HashMap<Room, Boolean> missionCompleteMap;        // stores whether the player completed the mission      ---> player class (extendable)
    private boolean isWinCharacter;
    
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        lastRoomList = new ArrayList<>();
        pickedUpItemList = new ArrayList<>();
        totalWeightPickedUp = 0;
        missionCompleteMap = new HashMap<>();
    }

    /**
     * Set the current room the player is located.
     *
     * @param    currentRoom The room the player is currently in.
     */
    public void goRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
    
    /**
     * Return the current room the player is located.
     * 
     * @return The room the player is currently in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Add the room the player currently in to the list of the rooms the player visited.
     * 
     * @parm lastRoom The room player visited.
     */
    public void addLastRoom()
    {
        lastRoomList.add(currentRoom);
    }
    
    /**
     * Return the list of the rooms the player visitied.
     * 
     * @return The list of the rooms the player visitied.
     */
    public ArrayList<Room> getLastRoomList()
    {
        return lastRoomList;
    }
    
    /**
     * Try to go back to the previous room.
     */
    public void goBack()
    {
        currentRoom = lastRoomList.get(lastRoomList.size()-1);
        lastRoomList.remove(lastRoomList.size()-1);
    }
    
    /**
     * Add the picked up item to the list of items carried, if the total weight including the adding item is less than 1,000 grams.
     * Otherwise, print an error message.
     * 
     * @parm The item to pick up.
     */
    public void pickUp(Item itemToPickUp) 
    {
        if(totalWeightPickedUp + itemToPickUp.getWeight() <= 1000)
        {
            pickedUpItemList.add(itemToPickUp);
            addTotalWeightPickedUp(itemToPickUp.getWeight());
            itemToPickUp.switchIsCarried();
            System.out.println("* You picked up " + itemToPickUp.getItemName() + " *");
        }
        else
        {
            System.out.println("You're already carrying more than 1,000 grams of items!");
        }        
    }
    
    /**
     * Return the list of items picked up by the player.
     * If there's no item picked up, return null.
     * 
     * @return The list of items picked up by the player.
     */
    public ArrayList<Item> getPickedUpItemList() 
    {
        return pickedUpItemList;
    }
    
    /**
     * Return a certain item found in the list of items picked up by the player.
     * If the item isn't found, return null.
     * 
     * @param itemName The name of the item to get.
     * @return The desired item.
     */
    public Item getItemFromPocket(String searchItemName) 
    {
        for(Item item: pickedUpItemList)
        {
            if(item.getItemName().equals(searchItemName))
            {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Increment the total weight of the items picked up by the weight given.
     * 
     * @parm weightAdd The weight to add to the total weight of the items picked up.
     */
    public void addTotalWeightPickedUp(int weightToAdd)
    {
        totalWeightPickedUp += weightToAdd;
    }
    
    /**
     * Return the total weight of the list of items currently carried by the player.
     * 
     * @return The total weight of the items currently carried.
     */
    public int getTotalWeightPickedUp()
    {
        return totalWeightPickedUp;
    }
    
    /**
     * Return a description of the list of items picked up by the user in the form:
     *     "============ POCKET with N items ============"
     *     "| NAME (DESCRIPTION) WEIGHT grams |"
     *     ...
     *     "========= TOTAL WEIGHT: A grams ==========="
     *     
     * @return The description of the list of items in the room.
     */
    public String getPickedUpItemListDescription()
    {
        String returnString = "========== POCKET with "+ pickedUpItemList.size() + " items ==========";
        
        int totalWeight = 0;
        for(Item pickedUpItem: pickedUpItemList)
        {
                returnString += "\n|" + pickedUpItem.getItemName() + "     (" + pickedUpItem.getWeight() + "gram(s)|";
                totalWeight += pickedUpItem.getWeight();
        }
        
        returnString += "\n========= TOTAL WEIGHT: " + totalWeight + " gram(s)=========";
        
        return returnString;
    }
    
    /**
     * Put a room to the mission complete map.
     * 
     * @parm roomToPut The room to put to the mission complete map.
     */
    public void putRoomMap(Room roomToPut)
    {
        missionCompleteMap.put(roomToPut, false);
    }
    
    /**
     * Set the mission of the room is completed.
     * 
     * @parm missionRoom The room where the mission is completed.
     */
    public void missionCompleted(Room missionRoom)
    {
        missionCompleteMap.replace(missionRoom, true);
    }
    
    /**
     * Return the map showing whether the player completed the mission.
     * 
     * @return The mission complete map.
     */
    public HashMap<Room, Boolean> getMissionCompleteMap()
    {
        return missionCompleteMap;
    }
    
    /**
     * Throw away an item in the pocket. Remove the item from the item list being carried
     * and update the total weight of items carried if there exists the item in the pocket,
     * otherwise print an error message.
     * 
     * @parm itemToDump The item to throw away.
     */
    public void dumpItem(Command command)
    {
        if(!command.hasNthWord(2)) {
            // if there is no second word, we don't know which item the player to dump.
            System.out.println("Dump which item?");
            return;
        }
        Item itemToDump = getItemFromPocket(command.getNthWord(2));
        if(pickedUpItemList.contains(itemToDump))
        {
            pickedUpItemList.remove(itemToDump);
            totalWeightPickedUp -= itemToDump.getWeight();
            itemToDump.switchIsCarried();
            System.out.println(itemToDump.getItemName() + " is dumped.");
            System.out.println(getPickedUpItemListDescription());
        }
        else
        {
            System.out.println("The item to dump can't be found in your pocket!");
        }
    }
    
    /**
     * Return the mission the player didn't complete yet.
     * If the player cleared all of the missions, return null.
     * 
     * Reference: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Map.Entry.html
     * 
     * @return The String information of the mission(s) that has(have) to be cleared.
     */
    public String getMissionNotCompleted()
    {
        for(HashMap.Entry<Room, Boolean> entry : missionCompleteMap.entrySet())
        {
            boolean isComplete = entry.getValue();
            System.out.println("getMissionNotCompleted() " + entry.getValue());
            if(!isComplete)
            {
                return "The mission in " + entry.getKey().getRoomName() + " is not completed."; 
            }
        }
        return null;
    }
}

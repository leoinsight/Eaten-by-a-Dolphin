import java.util.Random;
import java.util.ArrayList;

/**
 * This class is part of the "Eaten by a Dolphin" application. 
 * "Eaten by a Dolphin" is a highly interesting, text based adventure game.
 * 
 * A character object stores its name, phrase to talk, an item belonged to it, and the room where it's currently locating.
 * The character can give its item away to the player when it loses rock-scissors-paper.
 * 
 * 
 * @ SeoHyun Jen Kwon (k21070195)
 * @ 2021.11.
 */
public class GameCharacter
{
    private String characterName;
    private String talkPhrase;          // stores phrase to display when the player talks to it
    private Item itemBelong;            // stores an item it owns. The player can take it 
    private Room roomLocating;          // stores a room it currently locates.
    private boolean hasItem;            // if it has an item, it can play rock-paper-scissors with the player to give away its item.
    
    /**
     * Constructor for objects of class Charcter.
     * All of the characters are created in blowhole, as it is less possible to meet the character
     * right after they're created.
     */
    public GameCharacter(String characterName, String talkPhrase, Item itemBelong, Room initialRoom)
    {
        // initialise instance variables with given parameters
        this.characterName = characterName;
        this.talkPhrase = talkPhrase;
        this.itemBelong = itemBelong;
        this.roomLocating = initialRoom;     
        this.hasItem = true;
    }

    /**
     * Return the name of the character.
     * 
     * @return The name of the character.
     */
    public String getCharacterName()
    {
        return characterName;
    }
    
    /**
     * Return the phrase to display when the player talk to it.
     * 
     * @return The phrase to display when talking with the player.
     */
    public String getTalkPhrase()
    {
        return talkPhrase;
    }
    
    /**
     * Set a created item that belongs to the character.
     * 
     * @parm anItemBelong The item that belongs to the character.
     */
    public void setItemBelong(String anItemName, String anItemDescription, int aWeight)
    {
        itemBelong = new Item(anItemName, anItemDescription, aWeight, null);
    }
    
    /**
     * Return the item that belongs to the charcter.
     * 
     * @return The item that belongs to the character.
     */
    public Item getItemBelong()
    {
        return itemBelong;
    }
    
    /**
     * Change the room the character is currently in.
     * 
     * It moves randomly around the rooms but it's biased.
     * If the dolphin is under the water level, it's three times more likely to appear in the room where the player currently is.
     * Otherwise, it never appears in the room where the player currently is.
     * 
     * @parm aRoomLocating
     */
    public void changeRoomLocating(ArrayList<Room> roomList,Room playerRoom, boolean isAboveWater)
    {
        int weights = 0;
        ArrayList<Room> roomListRandom = roomList;
        
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(roomListRandom.size());
        
        if(isAboveWater)
        {
            roomListRandom.remove(playerRoom);
            index = randomGenerator.nextInt(roomListRandom.size());
            roomLocating = roomListRandom.get(index);
        }
        else
        {
            for(int i=0; i<2; i++)
            {
                roomListRandom.add(playerRoom);
            }
            roomLocating = roomListRandom.get(index);
        }
    }
    
    /**
     * Get the room the character is currently in.
     * 
     * @return The room the character is currently in.
     */
    public Room getRoomLocating()
    {
        return roomLocating;
    }
    
    /**
     * Give the item that is belonged to the character.
     */
    public void giveItem()
    {
        hasItem = false;
    }
    
    /**
     * Return whether the character has its item or gave it to the player.
     * 
     * @return Whether the character has its item
     */
    public boolean getHasItem()
    {
        return hasItem;
    }
}

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Random;

/**
 *  This class is the main class of the "Eaten by a Dolphin" application. 
 *  "Eaten by a Dolphin" is a highly interesting, text based adventure game.  Users 
 *  can walk around some scenery and accomplish the missons to escape from dolphin's body.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 * @author  Michael Kölling, David J. Barnes, and SeoHyun Jen Kwon (k21070195)
 * @version 2021.11.
 */

public class Game 
{
    private Parser parser;
    private ArrayList<Room> roomList;
    private boolean isAboveWater;                      // whether the dolphin is currently above the water level            ---> game class (info hide)
    private ArrayList<GameCharacter> characterList;
    private ArrayList<Item> itemList;
    private Player player;
    private HashMap<Room, Item> missionItemMap;     // stores mission item for each room
    private RpsCommandWords rpsCommands;        // stores commands for playing rock-paper-scissors
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        roomList = new ArrayList<>();
        createRooms();
        itemList = new ArrayList<>();
        createItems();
        characterList = new ArrayList<>();
        createGameCharacters();
        missionItemMap = new HashMap<>();
        createMissionItemMap();
        
        parser = new Parser();
        isAboveWater = false;
        player = new Player();
        rpsCommands = new RpsCommandWords();
        
        player.goRoom(getRoom("throat"));  // start game at the throat of the dolphin
    }

    /**
     * Create all the rooms and initialize their fields. Store them in the room list.
     */
    private void createRooms()
    {
        Room throat, heart, lung, stomach, eye, skull, blowhole;
      
        // create the rooms
        throat = new Room("throat");
        heart = new Room("heart");
        lung = new Room("lung");
        stomach = new Room("stomach");
        eye = new Room("eye");
        skull = new Room("skull");
        blowhole = new Room("blowhole");

        
        // initialise room exits
        throat.setExit("east", lung);
        throat.setExit("west", heart);
        throat.setExit("south", eye);
        throat.setExit("north", skull);

        heart.setExit("east", throat);

        lung.setExit("east", stomach);
        lung.setExit("west", throat);
        
        eye.setExit("north", throat);
        
        skull.setExit("up", blowhole);
        skull.setExit("south", throat);
        
        blowhole.setExit("down", skull);
        
        
        // initialise (a) magic transporter room(s)
        stomach.setIsMagicTransporter();
        
        
        // initialise help information for each room
        throat.setHelpInformation("Tip [ Explore some other rooms and items in them :) ]");
        lung.setHelpInformation("Tip [ Think about the reason the dolphin continuously goes up above the water :D ]");
        stomach.setHelpInformation("Tip [ You need to carry out a little surgery X___X Poor shrimp... ]");
        eye.setHelpInformation("Tip [ There are some actions you can or can't do depending on where the dolphin is :o ]");
        skull.setHelpInformation("Tip [ You can change the position of the dolphin *___*");
        blowhole.setHelpInformation("Tip [ Remember when the dolphin opens its blow hole! ]");
        
        
        // initialise the messages to display when the player completes the mission in each room
        eye.setMissionCompleteMessage("~~~~~~~~~~ ... Looking outside ... Is the dolphin currently above the water level: ");
        skull.setMissionCompleteMessage("~~~~~~~~~~ ... Dolphin moving up above the water level ... ~~~~~~~~~~");
        blowhole.setMissionCompleteMessage("~~~~~~~~~~ ... Blowhole opening ... You can now escape!!! ~~~~~~~~~~");
        
        // put the created rooms in the list of the rooms
        roomList.add(throat);       
        roomList.add(heart);                  
        roomList.add(lung);                    
        roomList.add(stomach);                 
        roomList.add(eye);                      
        roomList.add(skull);                   
        roomList.add(blowhole);
    }

    /**
     * Create all the items and store them in item list.
     * Initialise the name of the items as the parser converts all the command words of the user input to lower case.
     */
    private void createItems()
    {
        Item telescope = new Item("telescope", "See the sea", 800, getRoom("throat"));
        itemList.add(telescope);
        
        Item coldAir = new Item("cold-air", "Cool and fresh air from outside", 500, getRoom("lung"));
        Item mediumAir = new Item("medium-air", "Not too cold, not too hot air", 400, getRoom("lung"));
        Item hotAir = new Item("hot-air", "Warm air that have circulated around the body", 300, getRoom("lung"));
        itemList.add(coldAir);
        itemList.add(mediumAir);
        itemList.add(hotAir);
        
        Item incomingBlood = new Item("incoming-blood", "Blood coming in to the heart after working and getting energy",
        50, getRoom("heart"));
        Item outgoingBlood = new Item("outgoing-blood", "Blood going out from the heart to do work and get energy",
        300, getRoom("heart"));
        itemList.add(incomingBlood);
        itemList.add(outgoingBlood);

        //Item window = new Item("window", "Window that shows the water level", 0, getRoom("eye"));
        
        Item tail = new Item("tail", "Fresh tail of the shrimp as a key to open the door.", 700, null);     
        itemList.add(tail);
        
        //Item lever = new Item("Lever", "Lever that makes the dolphin be higher than the water lever.", 0, getRoom("skull"));
        
        //Item escapeButton = new Item("escape-button", "A tail of shrimp needed to open the blowhole to escape.", 0, getRoom("blowhole"));
    
    }
    
    /**
     * Create all the game characters and store them in the character list.
     */
    private void createGameCharacters()
    {
        // initialise the list of charcters in the game
        GameCharacter shrimp = new GameCharacter("shrimp", "Hi, I'm shrimp. I can give you my tail if you win rock-paper-scissors agianst me.",
        getItem("tail"), getRoom("blowhole"));
        characterList.add(shrimp);      
    }    

    /**
     * Create the map storing the mission item for a corresponding room.
     */
    private void createMissionItemMap()
    {
        missionItemMap = new HashMap<>();
        
        // initialise the items need to be used to complete the mission in some rooms
        missionItemMap.put(getRoom("eye"), getItem("telescope"));
        missionItemMap.put(getRoom("skull"), getItem("hot-air"));
        missionItemMap.put(getRoom("blowhole"), getItem("tail"));
    }
    
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            
            finished = processCommand(command);
            System.out.println("play()  " + getRoom("blowhole"));
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Eaten by a Dolphin!");
        System.out.println("Eaten by a Dolphin is a one-and-only exciting adventure game.");
        System.out.println("You are eaten by a dolphin and you need to escape out of it through the blow hole!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(!command.hasNthWord(1)) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getNthWord(1);
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.addLastRoom();
            tryGoRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("back")) {
            tryGoBack();
        }
        else if(commandWord.equals("view"))
        {
            checkPrintItems(command);
        }
        else if(commandWord.equals("pocket"))
        {
            System.out.println(player.getPickedUpItemListDescription());
        }
        else if(commandWord.equals("pick"))
        {
            tryPickUp(command);
        }
        else if(commandWord.equals("use"))
        {
            useItem(command);
        }
        else if(commandWord.equals("dump"))
        {
            player.dumpItem(command);
        }
        else if(commandWord.equals("talk"))
        {
            talkCharacter(command);
        }
        else if(commandWord.equals("throw"))
        {
            rockPaperScissors(command);
        }
        else if(commandWord.equals("escape"))
        {
            tryEscape(command);
        }
        
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println(player.getCurrentRoom().getHelpInformation());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * Everytime the player moves, the character moves too.
     * 
     * @parm command The command created from the words the user typed in.
     */
    private void tryGoRoom(Command command) 
    {
        if(!command.hasNthWord(1)) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(command.getNthWord(2));
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
            return;
        }
        
        if (nextRoom.getIsMagicTransporter()){
            magicTransportation();
            moveAllCharacter();
        }
        else
        {
            player.goRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
            moveAllCharacter();
        }
    }
    
    /**
     * Move all of the characters and notice the player if any of them is in the same room with the plaeyr.
     */
    private void moveAllCharacter()
    {
        for(GameCharacter gameCharacter: characterList)
        {
            gameCharacter.changeRoomLocating(roomList, player.getCurrentRoom(), isAboveWater);
    
            //System.out.println(gameCharacter.getCharacterName() + " in " + gameCharacter.getRoomLocating().getRoomName());
            if(gameCharacter.getRoomLocating().equals(player.getCurrentRoom()))
            {
                System.out.println(gameCharacter.getCharacterName() + " is currently in the same room with you.");                    
            }
        }
    }
    
    /**
     * Do the magic transportation.
     * Move the player to the random room until he is not in the magic transportation room.
     */
    private void magicTransportation()
    {
        System.out.println("+-*-+-*-+-*-+-*-+-* Magic Trasnportation +-*-+-*-+-*-+-*-+-*");
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(roomList.size());
        player.goRoom(roomList.get(index));
        System.out.println(player.getCurrentRoom().getLongDescription());
        
        while(player.getCurrentRoom().getIsMagicTransporter())
        {
            System.out.println("+-*-+-*-+-*-+-*-+-* Magic Trasnportation +-*-+-*-+-*-+-*-+-*");
            index = randomGenerator.nextInt(roomList.size());
            player.goRoom(roomList.get(index));
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasNthWord(2)) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "Escape" was enterd.Check the rest of the command
     * and whether the player can escape and win the game.
     * 
     * @return true, if the player won the game, false otherwise.
     */
    private void tryEscape(Command command)
    {
        if(command.hasNthWord(2))
        {
            System.out.println("Escape what?");
        }
        else if(!(player.getMissionNotCompleted() == null))
        {
            System.out.println(player.getMissionNotCompleted() + "\n You failed to escape from the dolphin." 
            + "\n:::::::::::GAME OVER:::::::::::");
            System.exit(0);
        }
        else
        {
            if(!isAboveWater)
            {                                          
                System.out.println("The dolphin is now under water. Blowhole not opened!");
            }
            else if(!(player.getCurrentRoom().equals(getRoom("blowhole"))))
            {
                System.out.println("tryEscape() " + getRoom("blowhole"));
                System.out.println("You can escape only through the blowhole.");
            }
            else
            {
                System.out.println("Hooray! You successfully escaped from the dolphin!"
                + "\n:::::::::::YOU WON:::::::::::");
                System.exit(0);
            }            
        }
    }
    
    
    /**
     * Try to go back to the last room the player visited, if there's a room the player visited.
     * Otherwise, print an error message.
     */
    private void tryGoBack()
    {
        if(player.getLastRoomList() == null)
        {
            System.out.println("No room to go back!");
        }
        else
        {
            player.goBack(); 
        }
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    /**
     * "View" was entered. If the user enters "view items", print the items in the room.
     * Otherwise, print error message.
     * 
     * @parm Command The command the user typed in.
     */
    private void checkPrintItems(Command command)
    {
        if(!command.hasNthWord(2) || !command.getNthWord(2).equals("items")) {
            // if there is no second word, we don't know if the user wants to view the items.
            System.out.println("Do you want to view the items in the room? Type in \"view items\".");
        }
        else
        {
            System.out.println(getItemsInformationCurrentRoom());
        }
    }
        
    /**
     * Return the information of items in the given room, in the form of:
     *      "NAME (DESCRIPTION) WEIGHT grams"
     *   or "NAME (DESCRIPTION) You can't pick up this item." if the item can't be carried.
     * if there is any item in the room/game.
     * Otherwise, print an error message.
     * 
     * @parm aRoom The room where the items initally locates.
     * @return The String information of item in the given room.
     */
    private String getItemsInformationCurrentRoom()
    {
        String returnString="";
        
        if(itemList != null)
        {
            for(Item item: itemList)
            {
                if(!(item.getInitialRoom() == null) && item.getInitialRoom().equals(player.getCurrentRoom()))
                {
                    returnString += item.getItemInformation();
                }
            }
            
            if(returnString.equals(""))
            {
                returnString += "No items in this room!";
            }
        }
        else
        {
            returnString += "No items in this game!";
        }
        
        return returnString;
    }

    
    /** 
     * Try to pick up an item. If the item can be carried by the player,
     * and allow the player to pick up the item, otherwise print an error message.
     * 
     * @parm command The command used to specify which item to pick up.
     */
    private void tryPickUp(Command command) 
    {
        if (!command.hasNthWord(3)) {
            // if there is no second word, we don't know which item the user wants to pick up.
            System.out.println("Pick up which item?");
            return;
        }
        else if (!command.getNthWord(2).equals("up"))
        {
            // if the user types in a phrase other than "pick up", let the user know.
            System.out.println("Do you want to pick up the item? Type in \"pick up (item name)\".");
            return;
        }

        Item itemToPickUp = getItem(command.getNthWord(3));
        if (itemToPickUp == null || !itemToPickUp.getInitialRoom().equals(player.getCurrentRoom())) {
            System.out.println("There is no such item in this room!");
        }
        else if (itemToPickUp.getWeight() == 0) {
            System.out.println("This item can't be picked up!");
        }
        else if(itemToPickUp.getIsCarried()) {
            System.out.println("The item already exists in your pocket!");
        }
        else {
            player.pickUp(itemToPickUp);
        }
        
        // Print out the updated list of the picked up items.
        System.out.println(player.getPickedUpItemListDescription());
    }
    
    
    /**
     * Return an certain room found in the roomList by its name.
     * If the room isn't found, return null.
     * 
     * @parm searchRoomName The name of the room to search.
     * @return The desired room object.
     */
    private Room getRoom(String searchRoomName)
    {
        for(Room room: roomList)
        {
            if(room.getRoomName().equals(searchRoomName))
            {
                return room;
            }
        }
        return null;
    }
    
    /**
     * Return a certain item found in the itemList by its name.
     * If the item isn't found, return null.
     * 
     * @param itemName The name of the item to search.
     * @return The desired item object.
     */
    private Item getItem(String searchItemName) 
    {
        for(Item item: itemList)
        {
            if(item.getItemName().equals(searchItemName))
            {
                return item;
            }
        }
        return null;
    }


    /**
     * Return a character found in the characterList by its name.
     * If the character doesn't exist, return null.
     * 
     * @parm characterName The name of the character to get.
     * @return The desired character obejct.
     */
    private GameCharacter getCharacter(String searchCharacterName)
    {
        for(GameCharacter gameCharacter: characterList)
        {
            if(gameCharacter.getCharacterName().equals(searchCharacterName))
            {
                return gameCharacter;
            }
        }
        return null;
    }
    
    
    /**
     * Use an item in the pocket to complete the mission in the current room,
     * if the room has a mission to complete.
     * 
     * If the player succeeded to clear the mission, set the room as its mission is completed,
     * otherwise print an error message.
     * 
     * @parm itemToUse The item in the pocket to use to complete the mission.
     */
    private void useItem(Command command)
    {
        if(!command.hasNthWord(2)) {
            // if there is no second word, we don't know which item the player wants to use.
            System.out.println("Use which item?");
            return;
        }
        Item itemToUse = player.getItemFromPocket(command.getNthWord(2));
        if(itemToUse == null)
        {
            System.out.println("Item not found!");
            return;
        }
        
        Item missionItem = missionItemMap.get(player.getCurrentRoom());
        
        if(missionItemMap.containsKey(player.getCurrentRoom()))
        {
            if(itemToUse.equals(missionItem))
            {
                String missionCompleteMessage = player.getCurrentRoom().getMissionCompleteMessage();
                if(itemToUse.equals(getItem("telescope")))        // if the player uses telescope, show whether
                                                                  // the dolphin is currently above the water level
                {
                    System.out.println("isAboveWater " + isAboveWater);
                    missionCompleteMessage += isAboveWater +" ~~~~~~~~~~";
                }
                else if(itemToUse.equals(getItem("hot-air")))
                {
                    switchIsAboveWater();
                }
                player.missionCompleted(player.getCurrentRoom());
                System.out.println(missionCompleteMessage);
            }
            else
            {
                System.out.println("The item can't be used to complete the mission. Please try another item.");
            }
        }
        else
        {
            System.out.println("There is no mission to complete in this room!");
        }
    }
    
    /**
     * Try to talk to the given character.
     * Print out the talking phrase of the character if the user input is valid and the charcter is in the same room.
     * Otherwise, print error message.
     * 
     * @parm command The command the user typed in.
     */
    private void talkCharacter(Command command)
    {
        if(!command.hasNthWord(2))
        {
            // if there is no second word, we don't know which item the plyaer wants to talk.
            System.out.println("Talk to which character?");
            return;
        }
        
        GameCharacter characterToTalk = getCharacter(command.getNthWord(2));
        
        if(characterToTalk == null || !(characterToTalk.getRoomLocating().equals(player.getCurrentRoom())))
        {
            System.out.println("Such character does not exist in the room you're in.");

        }
        else
        {
            System.out.println(characterToTalk.getTalkPhrase());

        }
    }
    
    /**
     * Play rock-paper-scissors with the character the player met.
     * Print out whether the player won rock-paper-scissors against the character, if the user input is valid,
     * the charcter can play rock-paper-scissors, and it is currently in the same room with the player.
     * Otherwise, print error message.
     * 
     * If the player wins, he acquires the item from the character. If they are even, they play it again.
     * Otherwise, the character leaves the room.
     * 
     * @parm command The command the user typed in.
     */
    private void rockPaperScissors(Command command)
    {   
        if(!command.hasNthWord(2))
        {
             // if there is no second word, we don't know which charcter the player wants to play rock-paper-scissisors with.
            System.out.println("Play rock-paper-scissors with which character?");
            return;
        }
        if(!rpsCommands.isRpsCommand(command.getNthWord(3)))
        {
            // if there is no third word, we don't know which one the player threw among rock, paper, and scissisors.
            System.out.println("If you want to play rock-paper-scissors, type in \"throw (character name) (what)\".");
            return;
        }
        
        GameCharacter characterToPlay = getCharacter(command.getNthWord(2));

        if(!characterToPlay.getRoomLocating().equals(player.getCurrentRoom()))
        {
            System.out.println("The character is not in the same room with you.");
        }
        else if (!characterToPlay.getHasItem())
        {
            System.out.println("The character is not supposed to play rock-paper-scissors with you. It has no item.");
        }
        else
        {
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(4);     //  0-1: player wins, 2: even, 3: player loses
            
            if(command.getNthWord(3).equals("rock"))
            {
                if(index == 0 || index == 1)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw scissors. You won!");

                    player.pickUp(characterToPlay.getItemBelong());
                    if(characterToPlay.getItemBelong().getIsCarried())
                    {
                        characterToPlay.giveItem();
                        System.out.println(player.getPickedUpItemListDescription());
                    }
                }
                else if (index ==2)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw rock. It's a draw! Play it once more.");
                    command = parser.getCommand();
                    rockPaperScissors(command);
                }
                else
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw paper. You lost!"
                    + "\n" + characterToPlay.getCharacterName() + " moved its location.");
                    characterToPlay.changeRoomLocating(roomList, player.getCurrentRoom(), isAboveWater);
                }
            }
            else if(command.getNthWord(3).equals("paper"))
            {
                if(index == 0 || index == 1)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw scissors. You won!");
                    
                    player.pickUp(characterToPlay.getItemBelong());
                    if(characterToPlay.getItemBelong().getIsCarried())
                    {
                        characterToPlay.giveItem();
                        System.out.println(player.getPickedUpItemListDescription());
                    }                  
                }
                else if (index ==2)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw paper. It's a draw! Play it once more.");
                    command = parser.getCommand();
                    rockPaperScissors(command);
                }
                else
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw scissors. You lost!"
                    + "\n" + characterToPlay.getCharacterName() + " moved its location.");
                    characterToPlay.changeRoomLocating(roomList, player.getCurrentRoom(), isAboveWater);
                } 
            }
            else
            {
                if(index == 0)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw paper. You won!"
                    + "\n" + characterToPlay.getItemBelong().getItemInformation() + "       picked up.");
                }
                else if (index ==1)
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw scissors. It's a draw! Play it once more.");
                    command = parser.getCommand();
                    rockPaperScissors(command);
                }
                else
                {
                    System.out.println(characterToPlay.getCharacterName() + " threw rock. You lost!"
                    + "\n" + characterToPlay.getCharacterName() + " moved its location.");
                    characterToPlay.changeRoomLocating(roomList, player.getCurrentRoom(), isAboveWater);
                }
            }
        }
    }
    
    /**
     * Switch whether the dolphin is currently above the water level.
     */
    private void switchIsAboveWater()
    {
        isAboveWater = !isAboveWater;
    }
    
    /**
     * Return whether the dolphin is currently above the water level.
     */
    private boolean getIsAboveWater()
    {
        return isAboveWater;
    }
    
    
}

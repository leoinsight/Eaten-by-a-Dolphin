/**
 * This class is part of the "Eaten by a Dolphin" application. 
 * "Eaten by a Dolphin" is a highly interesting, text based adventure game. 
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling, David J. Barnes, and SeoHyun Jen Kwon (k21070195)
 * @version 2021.12
 */

public class RpsCommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "rock", "paper", "scissors"
    };

    /**
     * Constructor - initialise the command words.
     */
    public RpsCommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isRpsCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * Print all valid commands to System.out.
     */
    /*
    public void showAll() 
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
    */
}

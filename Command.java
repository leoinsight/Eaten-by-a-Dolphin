import java.util.ArrayList;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two strings: a command word and a second
 * word (for example, if the command was "take map", then the two strings
 * obviously are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Command
{
    private ArrayList<String> commandWordList;

    /**
     * Create a command object. Stores all seperate words in the list.
    
     * @param firstWord The list of all seperate words of the user input.
     */
    public Command(ArrayList<String> commandWordList)
    {
        this.commandWordList = commandWordList;
        //System.out.println("commandWordList in command object   " + commandWordList);
    }
    
    /**
     * Return the nth word of this command where n is from 1 to (total number of words).
     * The actual index of the word to access in the list is n-1.
     * If the command was not understood or there's no word of index  n in the list, the result is null.
     * 
     * @parm n The integer from 1 to point out the word to interpret.
     * @return nth word of the command
     */
    public String getNthWord(int n)
    {
        if(!(commandWordList == null) && commandWordList.size() >= n)
        {
            return commandWordList.get(n-1);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Return whether there exists nth word.
     * 
     * @n The integer from 1 to point out the word to interpret.
     * @return True if this command has a nth word.
     */
    public boolean hasNthWord(int n)
    {
        if(!(commandWordList == null) && commandWordList.size() >= n)
        {
            //System.out.println("hasNthWord  " + (commandWordList.get(n-1) == null));
            return (!(commandWordList.get(n-1) == null));
        }
        else
        {
            return false;
        }
    }
    
    
    /*
    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    /*
    public String getCommandWord()
    {
        return commandWord;
    }
    

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    /*
    public String getSecondWord()
    {
        return secondWord;
    }
    */

    /*
    /**
     * @return true if this command was not understood.
     */
    /*
    public boolean isUnknown()
    {
        return (commandWord == null);
    }
    */

    /*
    /**
     * @return true if the command has a second word.
     */
    /*
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
    */
}


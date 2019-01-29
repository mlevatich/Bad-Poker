
/**
 * Card is an object holding the number and suit of a playing card.
 * 
 * @author Max Levatich 
 * @version 2/2/16
 */
public class Card
{
    private int num = -1;
    private int suit = -1;
    /**
     * Constructs a card using the given number and suit.
     * 
     * @param tempNum   the number on the card
     * @param tempSuit  an integer representing the suit of the card
     */
    public Card(int tempNum, int tempSuit)
    {
        num = tempNum;
        suit = tempSuit;
    }

    /**
     * Returns the number of the card.
     * 
     * @return The number of the card
     */
    public int getNum()
    {
        return num;
    }

    /**
     * Returns the suit of the card, as an integer.
     * 
     * @return the suit of the card
     */
    public int getSuit()
    {
        return suit;
    }
    
    /**
     * Converts the number and suit of a card into a name.
     * 
     * @return the name of the card
     */
    public String getCardName()
    {
        String returnString = "";
        if(num == 14)
        {
            returnString+=("Ace");
        }
        else if(num == 11)
        {
            returnString+=("Jack");
        }
        else if(num == 12)
        {
            returnString+=("Queen");
        }
        else if(num == 13)
        {
            returnString+=("King");
        }
        else
        {
            returnString+=(num);
        }
        returnString+=(" of ");
        if(suit == 1)
        {
            returnString+=("Spades");
        }
        else if(suit == 2)
        {
            returnString+=("Hearts");
        }
        else if(suit == 3)
        {
            returnString+=("Diamonds");
        }
        else
        {
            returnString+=("Clubs");
        }
        return returnString;
    }
}

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Player creates a poker player with a hand of cards, a name, and money.  The player can make bets and the dealer
 * interacts with the player.
 * 
 * @author Max Levatich
 * @version 2/2/16
 */
public class Player
{
    private ArrayList<Card> hand;
    private int money = -1;
    private int standingBet = -1;
    private String name = "";
    /**
     * Constructs a player with an empty hand, a name entered by the user, and some money.
     */
    public Player()
    {
        hand = new ArrayList<Card>();
        money = 100;
        standingBet = 0;
        Scanner myScan = new Scanner(System.in);
        System.out.print("Enter player name: ");
        name = myScan.nextLine(); //This is the only print statement outside of the driver. It's too convenient.
    }
    
    /**
     * Returns the player's name.
     * 
     * @return the player's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the player's current balance.
     * 
     * @return the player's money
     */
    public int getMoney()
    {
        return money;
    }

    /**
     * Returns the player's current hand.
     * 
     * @return the player's current hand
     */
    public ArrayList<Card> getHand()
    {
        return hand;
    }

    /**
     * Changes the players hand to the received hand.
     * 
     * @param newHand   the hand of cards to be swapped in
     */
    public void setHand(ArrayList<Card> newHand)
    {
        hand = newHand;
    }

    /**
     * Makes a bet for the player and saves it at a variable.
     */
    public void makeBet()
    {
        standingBet = (int)(Math.random()*10+1); //Bets are randomly decided.
        money -= standingBet;
    }

    /**
     * Returns the player's standing bet.
     * 
     * @return the player's bet
     */
    public int getBet()
    {
        return standingBet;
    }

    /**
     * Clears the player's bet after a round is over.
     */
    public void clearBet()
    {
        standingBet = 0;
    }

    /**
     * Adds a card to the player's hand.
     * 
     * @param card  the card to be added
     */
    public void addCard(Card card)
    {
        hand.add(card);
    }

    /**
     * Adds the appropriate amount of money to a winning player.
     * 
     * @param winnings  the amount of money to be added
     */
    public void addMoney(int winnings)
    {
        money += winnings;
    }
}

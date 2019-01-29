import java.util.ArrayList;
import java.util.Collections;
/**
 * Dealer is the primary class controlling the poker game. It stores the deck, an array of players, the hand size,
 * and the money pot. It removes players, adds players, deals and collects cards, shuffles the deck, takes bets,
 * calculates the winning hands, and distributes the winnings.
 * 
 * @author Max Levatich
 * @version 2/3/16
 */
public class Dealer
{
    private ArrayList<Player> players;
    private Deck myDeck;
    private int handSize = -1;
    private int pot = 0;
    /**
     * Constructs a dealer who orchestrates a poker game with a given number of players, hand size,
     * and deck.
     * 
     * @param numOfPlayers  the number of players in the game
     * @param tempHandSize  the size of each player's hand
     */
    public Dealer(int numOfPlayers, int tempHandSize)
    {
        handSize = tempHandSize;
        myDeck = new Deck();
        players = new ArrayList<Player>();
        for(int i=0; i<numOfPlayers; i++)
        {
            players.add(new Player());
        }
    }

    /**
     * Returns the array of players in the game.
     * 
     * @return the array of players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     * Adds a player to the game.
     */
    public void addPlayer()
    {
        players.add(new Player());
    }

    /**
     * Removes a number of players from the game, based off of a received array of players.
     * 
     * @param leavers   an array of the indexes of the players to be removed
     */
    public void removePlayers(ArrayList<Integer> leavers)
    {
        Collections.sort(leavers); //It ain't no passing craaaaazee....collections-dot-sort!!!
        Collections.reverse(leavers);
        for(int i=0; i<leavers.size(); i++)
        {   //I change the order so that the positions in the array aren't affected when people leave.
            players.remove(leavers.get(i)-1);
        }
    }

    /**
     * Removes all cards from the players' hands, in preparation for a new round.
     */
    public void collectCards()
    {
        for(int i=0; i<players.size(); i++)
        {
            players.get(i).clearBet();
            for(int j=0; j<handSize; j++)
            {
                players.get(i).getHand().remove(0);
            }
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck()
    {
        myDeck.shuffle();
    }

    /**
     * Returns the pot containing all of the bets.
     * 
     * @return the pot
     */
    public int getPot()
    {
        return pot;
    }

    /**
     * Deals cards from the deck to all of the players according to the hand size.
     */
    public void deal()
    {
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<players.size(); j++)
            {
                Card drawnCard = myDeck.draw();
                players.get(j).addCard(drawnCard);
            }
        }
    }

    /**
     * Takes bets from all of the players.
     * 
     * @return the array of bets
     */
    public ArrayList<Integer> takeBets()
    {
        ArrayList<Integer> bets = new ArrayList<Integer>();
        for(int i=0; i<players.size(); i++)
        {
            players.get(i).makeBet();
            bets.add(players.get(i).getBet());
            pot += players.get(i).getBet();
        }
        return bets;
    }

    /**
     * Swaps cards received cards from the player's hands with new cards from the top of the deck.
     * 
     * @param player    the player swapping cards
     * @param cardSwaps the array of indexes of cards to be swapped out
     */
    public void swapCards(int player, ArrayList<Integer> cardSwaps)
    {
        ArrayList<Card> playerHand = players.get(player).getHand();
        for(int i=0; i<cardSwaps.size(); i++)
        {
            playerHand.set(cardSwaps.get(i)-1 , myDeck.draw());
        }
        players.get(player).setHand(playerHand);
    }

    /**
     * Returns a string describing the player's hand.
     * 
     * @param a the integer value of the hand
     * @return the string describing the hand
     */
    public String handString(int a)
    {
        int i = 100000000;
        if(a>=9*i)
            return "A Royal Flush, the rarest and best hand in poker! Holy cow!";
        else if(a>=8*i)
            return "A Straight Flush! Amazing!";
        else if(a>=7*i)
            return "Four of a Kind! Impressive!";
        else if(a>=6*i)
            return "A Full House!";
        else if(a>=5*i)
            return "A Flush!";
        else if(a>=4*i)
            return "A Straight!";
        else if(a>=3*i)
            return "Three of a Kind";
        else if(a>=2*i)
            return "Two Pairs";
        else if(a>=i)
            return "One Pair";
        else
            return "Worth nothing! How unfortunate!";
    }

    /**
     * Returns an integer number.  The higher the number, the better the hand.
     * 
     * @param hand  the hand to be ranked
     * @return the integer identifying the hand's value
     */
    public int calculateHandValue(ArrayList<Card> hand)
    {   //Oh god this method is a disaster, at least it works.
        int returnInt = 0;

        //pair
        boolean flag6 = false;
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(j!=i && hand.get(i).getNum() == hand.get(j).getNum())
                {
                    flag6 = true;
                    ArrayList<Integer> outsidePair = new ArrayList<Integer>();
                    for(int k=0; k<handSize; k++)
                    {
                        if(k!= j && k!= i)
                        {
                            outsidePair.add(hand.get(k).getNum());
                        }
                    }
                    Collections.sort(outsidePair);
                    Collections.reverse(outsidePair);
                    returnInt = 100000000+hand.get(i).getNum()*1000000+outsidePair.get(0)*10000;
                    returnInt += outsidePair.get(1)*100+outsidePair.get(2);
                }
            }
        }

        //two pairs
        boolean flag11 = true;
        if(!flag6)
        {
            flag11 = false;
        }
        int ppos1 = -1;
        int ppos2 = -1;
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(flag11 && j!=i && hand.get(i).getNum() == hand.get(j).getNum())
                {
                    ppos1 = i;
                    ppos2 = j;
                }
            }
        }
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(flag11&&j!=i&&i!=ppos1&&i!=ppos2&&j!=ppos1&&j!=ppos2&&hand.get(i).getNum()==hand.get(j).getNum())
                {
                    for(int k=0; k<handSize; k++)
                    {
                        if(k!=i && k!=j && k!=ppos1 && k!=ppos2)
                        {
                            returnInt = 200000000+Math.max(hand.get(j).getNum(),hand.get(ppos1).getNum())*1000000;
                            returnInt+=Math.min(hand.get(j).getNum(),hand.get(ppos1).getNum())*10000;
                            returnInt+=hand.get(k).getNum()*100;
                        }
                    }
                }
            }
        }

        //three of a kind
        boolean flag7 = false;
        boolean flag8 = true;
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                for(int k=0; k<handSize; k++)
                {
                    if(flag8 && j!=i&&i!=k&&k!=j)
                    {
                        if(hand.get(i).getNum()==hand.get(j).getNum()&&hand.get(i).getNum()==hand.get(k).getNum())
                        {
                            flag7 = true;
                            returnInt = 300000000+hand.get(i).getNum()*1000000;
                        }
                    }
                }
            }
        }

        //straight
        int maxNum = 0;
        int maxPos = -1;
        int secondParameter = 0;
        boolean flag3 = false;
        for(int i=0; i<handSize; i++)
        {
            int num = hand.get(i).getNum();
            if(num>maxNum)
            {
                maxNum = num;
                maxPos = i;
            }
        }

        for(int j=0; j<2; j++)
        {
            int previous = maxNum;
            boolean flag2 = true;
            int total = 1;
            while(flag2)
            {
                flag2 = false;
                for(int i=0; i<handSize; i++)
                {
                    if(previous-hand.get(i).getNum() == 1)
                    {
                        flag2 = true;
                        total++;
                        previous = hand.get(i).getNum();
                    }
                }
            }
            if(total == 5)
            {
                returnInt = 400000000+maxNum*1000000;
                secondParameter = maxNum*1000000;
                flag3 = true;
            }
            if(maxNum == 14)
            {
                maxNum = 6;
            }
        }

        //flush
        int prevSuit = hand.get(0).getSuit();
        boolean flag4 = true;
        boolean flag5 = false;
        for(int i=0; i<handSize; i++)
        {
            if(hand.get(i).getSuit() != prevSuit)
            {
                flag4 = false;
            }
            prevSuit = hand.get(i).getSuit();
        }
        if(flag4)
        {
            returnInt = 500000000;
            flag5 = true;
        }

        //full house
        boolean flag10 = true;
        if(!flag7)
        {
            flag10 = false;
        }
        int fpos1 = -1;
        int fpos2 = -1;
        int fpos3 = -1;
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(flag10 && j!=i && hand.get(i).getNum() == hand.get(j).getNum())
                {
                    fpos1 = i;
                    fpos2 = j;
                }
            }
        }
        for(int i=0; i<handSize; i++)
        {
            if(flag10 && i!=fpos1 && i!=fpos2 && hand.get(i).getNum() == hand.get(fpos1).getNum())
            {
                fpos3 = i;
            }
        }

        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(flag10 && i!=fpos1 && i!=fpos2 && i!=fpos3 && j!=fpos1 && j!=fpos2 && j!=fpos3)
                {
                    if(j!=i && hand.get(i).getNum() == hand.get(j).getNum())
                    {
                        ArrayList<Integer> handNums = new ArrayList<Integer>();
                        for(int k=0; k<handSize; k++)
                        {
                            handNums.add(hand.get(k).getNum());
                        }
                        Collections.sort(handNums);
                        Collections.reverse(handNums);
                        returnInt = 600000000+handNums.get(0)*1000000;
                    }
                }
            }
        }

        //four of a kind
        boolean flag9 = true;
        if(!flag6)
        {
            flag9 = false;
        }
        int pos1 = -1;
        int pos2 = -1;
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(j!=i && hand.get(i).getNum() == hand.get(j).getNum() && flag9)
                {
                    pos1 = i;
                    pos2 = j;
                }
            }
        }
        for(int i=0; i<handSize; i++)
        {
            for(int j=0; j<handSize; j++)
            {
                if(flag9&&j!=i&&i!=pos1&&i!=pos2&&j!=pos1&&j!=pos2&&hand.get(i).getNum()==hand.get(j).getNum())
                {
                    if(hand.get(j).getNum() == hand.get(pos1).getNum())
                    {
                        returnInt = 700000000+hand.get(j).getNum()*1000000;
                    }
                }
            }
        }

        //straight flush
        if(flag3 && flag5)
        {
            returnInt = 800000000+secondParameter;
        }

        //royal flush
        int previousSuit = hand.get(0).getSuit();
        boolean flag1 = true;
        for(int i=0; i<handSize; i++)
        {
            int num = hand.get(i).getNum();
            if(hand.get(i).getSuit() != previousSuit || num!=1 || num!=10 || num!=11 || num!=12 || num!=13)
            {
                flag1 = false;
            }
            previousSuit = hand.get(i).getSuit();
        }
        if(flag1)
        {
            returnInt = 900000000;
        }

        if(returnInt == 0)
        {
            ArrayList<Integer> cardsInHand = new ArrayList<Integer>();
            for(int k=0; k<handSize; k++)
            {
                    cardsInHand.add(hand.get(k).getNum());
            }
            Collections.sort(cardsInHand);
            Collections.reverse(cardsInHand);
            returnInt = cardsInHand.get(0)*1000000+cardsInHand.get(1)*10000+cardsInHand.get(2)*100+cardsInHand.get(3);
        }
        return returnInt;
    }

    /**
     * Returns an array of indexes of the winning players, based on the received list of hand values.
     * 
     * @param rankings  an array of integer values describing the worth of a certain hand
     * @return the array of indexes of the winning players
     */
    public ArrayList<Integer> calculateWinners(ArrayList<Integer> rankings)
    {
        ArrayList<Integer> winners = new ArrayList<Integer>();
        int winningNumber = 0;
        for(int i=0; i<rankings.size(); i++)
        {   //Finding the maximum number of the values received, aka the best hand.
            if(rankings.get(i) > winningNumber)
            {
                winningNumber = rankings.get(i);
            }
        }

        for(int i=0; i<rankings.size(); i++)
        {   //Adding any players with the highest hand value to the array of winning players.
            if(rankings.get(i) == winningNumber)
            {
                winners.add(i);
            }
        }
        return winners;
    }

    /**
     * Distributes the winnings according to which players won.
     * 
     * @param winningPlayers    the array of indexes of the winning players
     */
    public void distributePot(ArrayList<Integer> winningPlayers)
    {
        int winners = winningPlayers.size();
        int winnings = pot/winners; //the array is never of size zero.
        int extra = pot%winners;
        //I give any remainder to the first player, just to deal with it, since it's so rare/unimportant anyways.
        pot = 0; //Reset the pot.
        for(int i=0; i<winners; i++)
        {
            players.get(winningPlayers.get(i)).addMoney(winnings);
        }
        players.get(winningPlayers.get(0)).addMoney(extra);
    }
}

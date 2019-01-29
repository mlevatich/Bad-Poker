import java.util.ArrayList;
import java.util.Collections;
/**
 * Deck calls methods on a deck of 52 card objects.
 * 
 * @author Max Levatich
 * @version 2/2/16
 */
public class Deck
{
    private ArrayList<Card> drawPile;
    /**
     * Constructs a deck of 52 cards.
     */
    public Deck()
    {
        shuffle();
    }

    /**
     * Creates a new deck of 52 cards and shuffles it.
     */
    public void shuffle()
    {   //Shuffle actually makes a new deck from scratch. It's only called after the dealer takes players' cards.
        drawPile = new ArrayList<Card>();
        for(int i=1; i<=13; i++)
        {
            for(int j=1; j<=4; j++)
            {
                drawPile.add(new Card(i+1,j));
            }
        }
        Collections.shuffle(drawPile);
    }

    /**
     * Removes the top card from the deck and returns it.
     * 
     * @return the top card in the deck
     */
    public Card draw()
    {
        Card drawnCard = drawPile.get(0);
        drawPile.remove(0);
        return drawnCard;
    }
    
    /**
     * Returns the deck, an array of cards.
     * 
     * @return the deck
     */
    public ArrayList<Card> getDeck()
    {
        return drawPile;
    }
}

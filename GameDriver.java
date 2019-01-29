import java.util.Scanner;
import java.util.ArrayList;
/**
 * GameDriver drives a game of poker.  It focuses on print statements and interacting with the Dealer class.
 * 
 * @author Max Levatich
 * @version 2/3/16
 */
public class GameDriver
{
    public static void main(String[] args)
    {
        int handSize = 5; //Changing this edits the hand size for the entire program, although some parts of dealer
        //still won't work properly with a greater or smaller hand size.
        Scanner myScan = new Scanner(System.in);
        System.out.println("Welcome to the game of Poker!");
        System.out.print("Please enter number of players (between 2 and 6): ");
        int numOfPlayers = Integer.parseInt(myScan.nextLine());
        Dealer myDealer = new Dealer(numOfPlayers,handSize); //The dealer class does all the cool stuff.
        System.out.print("\f"); //I do this occasionally, gets rid of clutter.

        int k = 1; //Round number.
        while(1==1) //Game continues to play until System.exit(0) is called.
        {
            System.out.println("Round "+k+" Begins! The dealer presents the money totals:\n");
            for(int j=0; j<numOfPlayers; j++)
            {   //Print names and monies.
                String name = myDealer.getPlayers().get(j).getName();
                System.out.println(name+": $"+myDealer.getPlayers().get(j).getMoney());
            }
            System.out.println("");
            ArrayList<Integer> bets = myDealer.takeBets();
            for(int i=1; i<=numOfPlayers; i++)
            {   //Prints bets.
                System.out.println(myDealer.getPlayers().get(i-1).getName()+"'s bet: $"+bets.get(i-1));
            }
            System.out.println("Total pot size: $"+myDealer.getPot());
            System.out.println("\nThe dealer shuffles the deck and deals "+handSize+" cards to each player.");
            myDealer.shuffleDeck();
            myDealer.deal();
            for(int j=1; j<=numOfPlayers; j++)
            {   //This larger for loop represents a single player's turn.
                System.out.println("\n"+myDealer.getPlayers().get(j-1).getName()+"'s turn!\n");
                System.out.println(myDealer.getPlayers().get(j-1).getName()+"'s hand:");
                for(int i=0; i<handSize; i++)
                {   //Prints the player's hand.
                    Card card = myDealer.getPlayers().get(j-1).getHand().get(i);
                    System.out.println("Card "+(i+1)+": "+card.getCardName());
                }

                System.out.print("\nPlease enter the number of cards you would like to swap out (3 maximum): ");
                int numOfSwaps = Integer.parseInt(myScan.nextLine());
                ArrayList<Integer> cardSwaps = new ArrayList<Integer>();
                for(int i=0; i<numOfSwaps; i++)
                {   //Swaps cards.
                    System.out.println("Enter a number corresponding to a position in the above list of cards");
                    System.out.print("of a card you wish to swap out (1-5): ");
                    cardSwaps.add(Integer.parseInt(myScan.nextLine()));
                }
                myDealer.swapCards(j-1, cardSwaps);
                if(numOfSwaps != 0)
                {   //There aren't any swaps or a presentation of the new hand if the player doesn't swap.
                    System.out.println("\nThe dealer replaces chosen cards with new ones from the top of the deck.\n");
                    System.out.println(myDealer.getPlayers().get(j-1).getName()+"'s new hand: ");
                    for(int i=0; i<handSize; i++)
                    {   //Prints the modified hand!
                        Card card = myDealer.getPlayers().get(j-1).getHand().get(i);
                        System.out.println("Card "+(i+1)+": "+card.getCardName());
                    }
                }
            }
            System.out.println("");
            ArrayList<Integer> handRankings = new ArrayList<Integer>();
            for(int j=1; j<=numOfPlayers; j++)
            {   //Getting the values of each hand (i.e. how much is the hand worth).
                handRankings.add(myDealer.calculateHandValue(myDealer.getPlayers().get(j-1).getHand()));
                System.out.print(myDealer.getPlayers().get(j-1).getName()+"'s hand is: ");
                System.out.println(myDealer.handString(handRankings.get(j-1)));
            }
            ArrayList<Integer> winners = myDealer.calculateWinners(handRankings); //Sending hands to be ranked.
            System.out.println("");
            if(winners.size() == 1)
            {   //Is there just one winner, or is there a tie?
                System.out.print("The winner is "+myDealer.getPlayers().get((winners.get(0))).getName()+"!");
                System.out.println(" The dealer distributes the winnings and collects cards.");
            }
            else if(winners.size() > 1)
            {
                System.out.print("There is a tie between ");
                for(int i=0; i<winners.size(); i++)
                {
                    System.out.print(myDealer.getPlayers().get((winners.get(i))).getName()+" and ");
                }
                System.out.println("and as such, the pot will be split between them.");
            }
            myDealer.distributePot(winners);
            myDealer.collectCards();
            System.out.println("\nThe round is over!  The dealer presents the money totals:\n");
            for(int j=0; j<numOfPlayers; j++)
            {   //Printing money totals.
                String name = myDealer.getPlayers().get(j).getName();
                System.out.println(name+": $"+myDealer.getPlayers().get(j).getMoney());
            }
            System.out.print("\nEnter \"X\" to exit the game, anything else to continue playing: ");
            if(myScan.nextLine().equals("X"))
            {   //Quits the program if the user wants to.
                System.exit(0);
            }

            ArrayList<Integer> kickedPlayers = new ArrayList<Integer>();
            //Broke players and players who choose to leave are combined into a single array.
            System.out.println("");
            for(int j=0; j<numOfPlayers; j++)
            {   //Kicking anyone who is out of money.
                if(myDealer.getPlayers().get(j).getMoney()<0)
                {
                    kickedPlayers.add(j+1);
                    String name = myDealer.getPlayers().get(j).getName();
                    System.out.println("The dealer kicks "+name+" from the table for being out of money.");
                }
            }

            System.out.print("Please enter how many players will leave the game");
            System.out.print(" (must be less than the number of current players): ");
            int leavers = Integer.parseInt(myScan.nextLine());
            for(int j=0; j<leavers; j++)
            {   //Kicking any players who the user wants to leave.
                System.out.print("Which player is leaving?");
                System.out.print(" (enter a number according to their position on the above list): ");
                int answer = Integer.parseInt(myScan.nextLine());
                if(kickedPlayers.contains(answer))
                {
                    System.out.println("This player has already left.");
                    j--;
                }
                else
                {
                    kickedPlayers.add(answer);
                }
            }
            myDealer.removePlayers(kickedPlayers);
            int n = kickedPlayers.size();
            System.out.println("\n"+n+" player(s) have left the table in total, leaving "+(numOfPlayers-n));
            numOfPlayers -= n;
            System.out.print("Please enter how many new players will join the table (total may not exceed 5): ");
            int a = Integer.parseInt(myScan.nextLine());
            for(int j=0; j<a; j++)
            {   //Adding however many players the user wants to add.
                myDealer.addPlayer();
            }
            numOfPlayers += a;
            System.out.print("\f");
            k++; //Round number.
        }
    }
}

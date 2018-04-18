/**
 * This is the Straight Class
 * @author JaiTorres13 implemented structure of level & Game over
 * @author JTorres7 implemented score of level
 */

import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class StraightLevel extends FlushLevel{

	protected StraightLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}


	@Override
	protected void makeDeck() {
		// In Combo level the grid consists of distinct cards, no repetitions

		//back card
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
		}

		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);

		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new Card(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}

	int straightFound = 0;
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// We are uncovering the last card in this turn
				// Record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)
				Card otherCard1 = (Card) this.getTurnedCardsBuffer().get(0);
				Card otherCard2 = (Card) this.getTurnedCardsBuffer().get(1);
				Card otherCard3 = (Card) this.getTurnedCardsBuffer().get(2);
				Card otherCard4 = (Card) this.getTurnedCardsBuffer().get(3);
				int equal = 0;
				String [] ranks = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a"};
				String [] pokerHand = { otherCard1.getRank(), otherCard2.getRank(), otherCard3.getRank(), otherCard4.getRank(), card.getRank()};
				String [] testHand = {" ", " ", " ", " ", " "};

				for(int i = 0; i < 10; i++) {
					testHand[0] = ranks[i];
					testHand[1] = ranks[i + 1];
					testHand[2] = ranks[i + 2];
					testHand[3] = ranks[i + 3];
					testHand[4] = ranks[i + 4];
					if(Arrays.equals(testHand, pokerHand)) equal = 1;
				}

				if( equal == 1 &&  !((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
						(card.getSuit().equals(otherCard3.getSuit())) 
						&& (card.getSuit().equals(otherCard4.getSuit()))) ) 
				{
					this.getTurnedCardsBuffer().clear();
					sum += 1000;
					if(pokerHand[4].equals("t")) {
						sum += 100 * 10;
					}
					else if(pokerHand[4].equals("j")){
						sum += 100 * 11;
					}
					else if(pokerHand[4].equals("q")) {
						sum += 100 * 12;
					}
					else if(pokerHand[4].equals("k")) {
						sum += 100 * 13;
					}
					else if(pokerHand[4].equals("a")) {
						sum += 100 * 20;
					}
					else {
						sum += 100 * Integer.valueOf(pokerHand[4]);
					}

					this.getMainFrame().setScore(sum);
					straightFound++; 				}
				else {
					sum += -5;
					this.getMainFrame().setScore(sum);

					this.getTurnDownTimer().start();
				}
			}
		}


		return true;
	}


	@Override
	public boolean  isGameOver()
	{

		if(!(straightFound == 8)) return false;

		return true;
	}

}
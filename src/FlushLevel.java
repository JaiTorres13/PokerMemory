/**
 * This is the Flush Class
 * @author JaiTorres13 implemented structure of level & Game over
 * @author JTorres7 implemented score of level
 */

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends RankTrioLevel {

	// FLUSH LEVEL: The goal is to find, on each turn, five cards with the same suit

	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Flush Level");
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}

	@Override
	protected void makeDeck() {
		// In Flush level the grid consists of distinct cards, no repetitions

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

	int quintupletFound = 0;
	int sum = 0;

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
				String [] pokerHand = { otherCard1.getRank(), otherCard2.getRank(), otherCard3.getRank(), otherCard4.getRank(), card.getRank()};

				if((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && (card.getSuit().equals(otherCard3.getSuit())) 
						&& (card.getSuit().equals(otherCard4.getSuit()))) {
					// Five cards match, so remove them from the list (they will remain face up)
					sum += 700;
					for(int i = 0; i < 5; i++) {

						if(pokerHand[i].equals("t")) {
							sum += 10;
						}
						else if(pokerHand[i].equals("j")){
							sum += 11;
						}
						else if(pokerHand[i].equals("q")) {
							sum += 12;
						}
						else if(pokerHand[i].equals("k")) {
							sum += 13;
						}
						else if(pokerHand[i].equals("a")) {
							sum += 20;
						}
						else {
							sum += Integer.valueOf(pokerHand[i]);
						}
						this.getMainFrame().setScore(sum);
					}
					this.getTurnedCardsBuffer().clear();
					quintupletFound++;
				}

				else {
					sum += -5;
					this.getMainFrame().setScore(sum);
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}

		return false;

	}


	@Override
	public boolean  isGameOver()
	{

		if(!(quintupletFound == 8)) return false;

		return true;
	}

}

/**
 * This is the Combo Class
 * @author JaiTorres13 implemented structure of level & Game over
 * @author JTorres7 implemented score of level
 */
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JOptionPane;
import java.io.IOException;

public class ComboLevel extends EqualPairLevel  {
	private boolean nIsPressed = false;
	private boolean spaceIsPressed = false;

	//COMBO LEVEL: the goal is to reach a certain score while finding several poker hands
	protected ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		this.getTurnsTakenCounter().setDifficultyModeLabel("Combo Level");
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

	int sum = 0;
	int acceptPokerHand = 2;
	int gameOver = 0;
	@Override
	protected boolean turnUp(Card card) {

		//		int acceptPokerHand = 2;
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
				String [] pokerHand = {otherCard1.getRank(), otherCard2.getRank(), otherCard3.getRank(), otherCard4.getRank(), card.getRank()};
				String [] testHand = {" ", " ", " ", " ", " "};
				card.faceUp();
				for(int i = 0; i < 10; i++) {
					testHand[0] = ranks[i];
					testHand[1] = ranks[i + 1];
					testHand[2] = ranks[i + 2];
					testHand[3] = ranks[i + 3];
					testHand[4] = ranks[i + 4];
					if(Arrays.equals(testHand, pokerHand)) equal = 1;
				}
				System.out.println(equal);


				//Straight Hand
				if( equal == 1 &&  !((card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
						(card.getSuit().equals(otherCard3.getSuit())) 
						&& (card.getSuit().equals(otherCard4.getSuit()))) ) 
				{
					// Five consecutive cards is found, so remove them from the list (they will remain face up)
					acceptPokerHand = JOptionPane.showConfirmDialog(null, "Do you want to keep this Straight?", 
							"STRAIGHT FOUND!!", JOptionPane.YES_NO_OPTION);

					if(acceptPokerHand == 0) {
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
					}

					else {
						sum += -2500;
						this.getMainFrame().setScore(sum);
						this.getTurnDownTimer().start();

					}

					gameOver++;
				}

				//Flush Hand
				else if( (card.getSuit().equals(otherCard1.getSuit())) && (card.getSuit().equals(otherCard2.getSuit())) && 
						(card.getSuit().equals(otherCard3.getSuit())) 
						&& (card.getSuit().equals(otherCard4.getSuit())) ) {
					// Five cards match, so remove them from the list (they will remain face up)
					acceptPokerHand = JOptionPane.showConfirmDialog(null, "Do you want to keep this Flush?", 
							"FLUSH FOUND!!!", JOptionPane.YES_NO_OPTION);

					if(acceptPokerHand == 0) {
						this.getTurnedCardsBuffer().clear();
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
					}

					else {
						sum += -1000;
						this.getMainFrame().setScore(sum);
						this.getTurnDownTimer().start();


					}
					gameOver++;
				} 
				//Four of a Kind
				else if( ((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank())) && 
						(card.getRank().equals(otherCard3.getRank()))) || ((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank())) && 
								(card.getRank().equals(otherCard4.getRank()))) || ((card.getRank().equals(otherCard2.getRank())) && (card.getRank().equals(otherCard3.getRank())) && 
										(card.getRank().equals(otherCard4.getRank()))) || ((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard3.getRank())) && 
												(card.getRank().equals(otherCard4.getRank()))) || ((otherCard1.getRank().equals(otherCard2.getRank())) && (otherCard1.getRank().equals(otherCard3.getRank())) && 
														(otherCard1.getRank().equals(otherCard4.getRank())))) {
					//Four cards with same rank is found
					acceptPokerHand = JOptionPane.showConfirmDialog(null, "Do you want to keep this hand of Four of a Kind?", 
							"FOUR OF A KIND FOUND!!", JOptionPane.YES_NO_OPTION);

					if(acceptPokerHand == 0) {
						this.getTurnedCardsBuffer().clear();
						sum += 1500;
						this.getMainFrame().setScore(sum);
					}

					else {
						sum += -500;
						this.getMainFrame().setScore(sum);
						this.getTurnDownTimer().start();
					}


					gameOver++;
				}


				else {
					sum += -50;
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
		if(gameOver == 8) return true;
		return false;

	}

}

/**
 * This is the extended class of SameRankTrio
 * @author JaiTorres13 implemented Game over
 * @author JTorres7 implemented score of level
 */

import javax.swing.JFrame;

public class MyRankTrio extends RankTrioLevel {

	protected MyRankTrio(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
	}

	int trioFound = 0;
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
				if((card.getRank().equals(otherCard1.getRank())) && (card.getRank().equals(otherCard2.getRank()))) {
					// Three cards match, so remove them from the list (they will remain face up)
					sum += 0;
					if(card.getRank().equals("t")) {
						sum += 100 + 30;
					}
					else if(card.getRank().equals("j")){
						sum += 100 + 33;
					}
					else if(card.getRank().equals("q")) {
						sum += 100 + 36;
					}
					else if(card.getRank().equals("k")) {
						sum += 100 + 39;
					}
					else if(card.getRank().equals("a")) {
						sum += 100 + 60;
					}
					else {
						sum += 100 + Integer.valueOf(card.getRank()) + Integer.valueOf(card.getRank()) + Integer.valueOf(card.getRank());
					}
					this.getMainFrame().setScore(sum);
					this.getTurnedCardsBuffer().clear();
					trioFound++;
				}
				else
				{
					// The cards do not match, so start the timer to turn them down
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
		if(!(trioFound == 12)) return false;
		return true;
	}
}

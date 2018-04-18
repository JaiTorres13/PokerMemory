/**
 * This is the extended EqualPair Class 
 * @author JTorres7 implemented score of level
 */
import javax.swing.JFrame;

public class MyEqualPair extends EqualPairLevel {

	protected MyEqualPair(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame) {
		super(validTurnTime, mainFrame);
		super.getTurnsTakenCounter().setDifficultyModeLabel("Medium Level");
	}

	int sum = 0;
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			this.getTurnedCardsBuffer().add(card);
			if(this.getTurnedCardsBuffer().size() == getCardsToTurnUp())
			{
				// there are two cards faced up
				// record the player's turn
				this.getTurnsTakenCounter().increment();
				// get the other card (which was already turned up)
				Card otherCard = (Card) this.getTurnedCardsBuffer().get(0);
				// the cards match, so remove them from the list (they will remain face up)
				if( otherCard.getNum() == card.getNum())
					sum += 0;
				if(otherCard.getNum() == card.getNum()) {
					sum += 50;
					this.getMainFrame().setScore(sum);


					this.getTurnedCardsBuffer().clear();
				}
				// the cards do not match, so start the timer to turn them down
				else {
					sum += -5;
					this.getMainFrame().setScore(sum);
					this.getTurnDownTimer().start();
				}
			}
			return true;
		}
		// there are already the number of EqualPair (two face up cards) in the turnedCardsBuffer
		return false;
	}
}

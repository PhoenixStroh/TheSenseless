package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.actions.utility.WaitAction;

public class UpToDiscardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
  
    public static final String[] TEXT = uiStrings.TEXT;
    
    private AbstractPlayer p;
    
    private boolean anyNumber;
    
    private boolean canPickZero;
    
    public static int numExhausted;
    
    public UpToDiscardAction(int amount, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.DISCARD;
    }
    
    public UpToDiscardAction(boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(99, anyNumber, canPickZero);
    }
    
    public UpToDiscardAction(int amount, boolean canPickZero) {
        this(amount, false, canPickZero);
    }
    
    public UpToDiscardAction(int amount, boolean anyNumber, boolean canPickZero, float duration) {
       this(amount, anyNumber, canPickZero);
        this.duration = this.startDuration = duration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration)
        {
            //If the hand size is 0, end action
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            //If the number to discard is higher then the hand size, downgrade to hand size
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
            }

            //Open the hand card select screen, and tick duration forward.
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero, false, false, true);
            tickDuration();
            return;
        }

        //If the cards have been selected...
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) { //If the group isn't empty...
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) { //For each card, discard it 
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                }
            }
            
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            addToBot((AbstractGameAction)new WaitAction(0.25F));
            //this.isDone = true;
        } 
        tickDuration();   
    }  
}

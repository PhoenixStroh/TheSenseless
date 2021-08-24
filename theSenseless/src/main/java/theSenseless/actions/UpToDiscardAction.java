package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class UpToDiscardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
  
    public static final String[] TEXT = uiStrings.TEXT;
    
    private AbstractPlayer p;
    
    private boolean isRandom;
    
    private boolean anyNumber;
    
    private boolean canPickZero;
    
    public static int numExhausted;
    
    public UpToDiscardAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.DISCARD;
    }
    
    public UpToDiscardAction(boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(99, isRandom, anyNumber, canPickZero);
    }
    
    public UpToDiscardAction(int amount, boolean canPickZero) {
        this(amount, false, false, canPickZero);
    }
    
    public UpToDiscardAction(int amount, boolean isRandom, boolean anyNumber) {
       this(amount, isRandom, anyNumber, false);
    }
    
    public UpToDiscardAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, float duration) {
       this(amount, isRandom, anyNumber, canPickZero);
        this.duration = this.startDuration = duration;
    }
    
    @Override
    public void update() {
        // if (this.duration == this.startDuration) {
            //     if (this.p.hand.size() == 0) {
            //     this.isDone = true;
            //     return;
            // } 
            // if (!this.anyNumber && 
            // this.p.hand.size() <= this.amount) {
            //     this.amount = this.p.hand.size();
        //         numExhausted = this.amount;
        //         int tmp = this.p.hand.size();
        //         for (int i = 0; i < tmp; i++) {
        //             AbstractCard c = this.p.hand.getTopCard();
        //             this.p.hand.moveToDiscardPile(c);
        //             //this.p.hand.moveToExhaustPile(c);
        //         } 
        //         return;
        //     } 
        //     if (this.isRandom) {
        //         for (int i = 0; i < this.amount; i++)
        //             this.p.hand.moveToDiscardPile(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
        //             //this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng)); 
        //     } else {
        //         numExhausted = this.amount;
        //         AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
        //         tickDuration();
        //         return;
        //     }
        // } 
        // if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
        //     for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
        //         this.p.hand.moveToDiscardPile(c);
        //         //this.p.hand.moveToExhaustPile(c);
        //         AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        // } 
        // tickDuration();



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
            //this.isDone = true;
        } 
        tickDuration();   
    }  
}

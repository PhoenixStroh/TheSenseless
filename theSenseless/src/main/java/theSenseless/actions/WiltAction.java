package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import theSenseless.SenselessMod;
import theSenseless.relics.ScrollOfReflectionRelic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WiltAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SenselessMod.class.getName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup cardGroup;
    private CardGroup pickGroup;

    private boolean isRandom;
    private boolean ignoreCost;
    private String pileType;

    public WiltAction(String cardGroup, boolean ignoreCost, boolean random) {
        this.p = AbstractDungeon.player;
        isRandom = random;
        this.ignoreCost = ignoreCost;
        pileType = cardGroup;
        switch (cardGroup)
        {
            case "draw": this.cardGroup = this.p.drawPile; break;
            case "discard": this.cardGroup = this.p.discardPile; break;
            case "exhaust": this.cardGroup = this.p.exhaustPile; break;
        }

        pickGroup = new CardGroup(this.cardGroup.type);
        for (AbstractCard c : this.cardGroup.group)
        {
            logger.info("Sorting Wilt Deck. Cost: " + c.cost + " <= Energy: " + (EnergyPanel.totalCount - 1));
            if (c.cost <= EnergyPanel.totalCount - 1 || ignoreCost == true)
            {
                pickGroup.addToRandomSpot(c);
            }
        }

        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public WiltAction(boolean ignoreCost)
    {
        this("discard",ignoreCost,false);
    }

    public WiltAction(String cardGroup)
    {
        this(cardGroup,false,false);
    }

    public WiltAction()
    {
        this("discard",false,false);
    }

    public void update() {
        //If the battle is ending, end action.
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }
        // Play once
        if (this.duration == Settings.ACTION_DUR_MED) {
            //If the discard pile is empty, end action.
            if (pickGroup.isEmpty()) {
                this.isDone = true;
                return;
            } 
            //If the discard pile is only 1, automatically play card.
            if (pickGroup.size() == 1) {
                AbstractCard tmp = cardGroup.getTopCard();
                Wilt(tmp);
            } 
            //???
            if (pickGroup.group.size() > this.amount) {
                if (isRandom)
                {
                    //Because all cards are added to a random spot, this is effectively random
                    Wilt(pickGroup.getTopCard());
                }
                else
                {
                    AbstractDungeon.gridSelectScreen.open(pickGroup, 1, "Choose a card to play and exhaust.", false, false, false, false);
                    tickDuration();
                    return;
                }
            } 
        } 
        //If the cards are selected, do action.
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                Wilt(c);
            } 
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }

        tickDuration();
    }

    public void Wilt(AbstractCard c)
    {
        c.exhaust = true;
        cardGroup.group.remove(c);
        (AbstractDungeon.getCurrRoom()).souls.remove(c);

        addToBot((AbstractGameAction)new NewQueueCardAction(c, true, false, true));
        if (AbstractDungeon.player.hasRelic(ScrollOfReflectionRelic.ID) && pileType == "discard") {
            logger.info("REFLECTED!!!");
            AbstractCard tmpC = c.makeSameInstanceOf();
            tmpC.purgeOnUse = true;
            addToBot((AbstractGameAction)new NewQueueCardAction(tmpC, true, false, true));
        }

        if (!ignoreCost)
        {
            p.loseEnergy(c.cost);
        }
    }
}


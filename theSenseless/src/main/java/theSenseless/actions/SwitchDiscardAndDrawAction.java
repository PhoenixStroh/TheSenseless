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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwitchDiscardAndDrawAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SenselessMod.class.getName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup discardGroup;
    private CardGroup drawGroup;

    public SwitchDiscardAndDrawAction() {
        this.p = AbstractDungeon.player;
        this.discardGroup = p.discardPile;
        this.drawGroup = p.drawPile;

        this.actionType = AbstractGameAction.ActionType.SHUFFLE;
        this.duration = Settings.ACTION_DUR_LONG;
    }

    public void update() {
        //If the battle is ending, end action.
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_LONG)
        {
            CardGroup tmpGroup = new CardGroup(discardGroup.type);
    
            logger.info("FirstGroup Size: " + discardGroup.size());
            logger.info("SecondGroup Size: " + drawGroup.size());

            //Add DrawGroup Cards to tempGroup
            for (AbstractCard c : drawGroup.group)
            {
                tmpGroup.addToTop(c);
            }            
            
            //Move all discard cards to draw cards
            while (discardGroup.size() > 0)
            {
                AbstractCard tmp = discardGroup.getTopCard();
                discardGroup.removeCard(tmp);
                discardGroup.moveToDeck(tmp, false);
            }

            //Move all tempGroup cards from draw pile to discard pile

            for (AbstractCard c : tmpGroup.group)
            {
                drawGroup.removeCard(c);
                drawGroup.moveToDiscardPile(c);                
            }
        }
        
        tickDuration();
    }
}

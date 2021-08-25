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

public class SwitchPilesAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(SenselessMod.class.getName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardPileToTopOfDeckAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup firstGroup;
    private CardGroup secondGroup;

    public SwitchPilesAction(CardGroup firstGroup, CardGroup secondGroup) {
        this.p = AbstractDungeon.player;
        this.firstGroup = firstGroup;
        this.secondGroup = secondGroup;

        this.actionType = AbstractGameAction.ActionType.SHUFFLE;
        this.duration = Settings.ACTION_DUR_LONG;
    }

    public void update() {
        //If the battle is ending, end action.
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }

        CardGroup firstTmpGroup = new CardGroup(firstGroup.type);
        CardGroup secondTmpGroup = new CardGroup(secondGroup.type);

        while (firstGroup.size() > 0)
        {
            firstTmpGroup.addToBottom(firstGroup.getTopCard()); 
            firstGroup.removeTopCard();
        }
        while (secondGroup.size() > 0)
        {
            secondTmpGroup.addToBottom(secondGroup.getTopCard()); 
            secondGroup.removeTopCard();
        }

        for (AbstractCard c : firstTmpGroup.group)
        {
            firstGroup.addToTop(c);
        }
        for (AbstractCard c : secondTmpGroup.group)
        {
            secondGroup.addToTop(c);
        }
        
        tickDuration();
        this.isDone = true;
    }
}

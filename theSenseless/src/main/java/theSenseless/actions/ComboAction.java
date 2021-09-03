package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ComboAction extends AbstractGameAction {
    private AbstractCard card;

    public ComboAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        this.card.baseDamage += this.amount;
        this.card.applyPowers();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            IncreaseCombo(c, this.amount);
        } 
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            IncreaseCombo(c, this.amount);
        } 
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            IncreaseCombo(c, this.amount);
        } 
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            IncreaseCombo(c, this.amount);
        } 
        this.isDone = true;
    }

    private void IncreaseCombo(AbstractCard c, int value)
    {
        if (c instanceof theSenseless.cards.CardComboStarter) {
            IncreaseDamage(c, value);
        } 
    }

    private void IncreaseDamage(AbstractCard c, int value)
    {
        c.baseDamage += value;
        c.applyPowers();
    }
}

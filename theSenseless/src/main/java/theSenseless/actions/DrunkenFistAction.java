package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theSenseless.powers.DrunkPower;

public class DrunkenFistAction extends AbstractGameAction {
  private DamageInfo info;
  
  public DrunkenFistAction(AbstractCreature target, DamageInfo info) {
    this.actionType = AbstractGameAction.ActionType.BLOCK;
    this.target = target;
    this.info = info;
  }
  
  public void update() {
    if (this.target != null && AbstractDungeon.player.hasPower(DrunkPower.POWER_ID)) {
      addToBot(new DrawCardAction(AbstractDungeon.player, 1));
    } 
    addToBot(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    this.isDone = true;
  }
}

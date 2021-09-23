package theSenseless.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import theSenseless.powers.DrunkPower;

public class BottomsUpAction extends AbstractGameAction {
    private boolean freeToPlayOnce;

    private static final int DRUNK = 2;

    private int drunk = DRUNK;
  
    private boolean upgraded;
    
    private AbstractPlayer p;
    
    private int energyOnUse;
  
  public BottomsUpAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
    this.actionType = AbstractGameAction.ActionType.BLOCK;
    this.freeToPlayOnce = freeToPlayOnce;
    this.energyOnUse = energyOnUse;
    this.p = p;
   // this.info = info;
  }
  
  public void update() {
    int effect = EnergyPanel.totalCount;
    if (this.energyOnUse != -1)
      effect = this.energyOnUse; 
    if (this.p.hasRelic("Chemical X")) {
      effect += 2;
      this.p.getRelic("Chemical X").flash();
    } 
    if (this.upgraded)
      effect++; 
    if (effect > 0) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrunkPower(p, p, effect*drunk), effect*drunk));
      if (!this.freeToPlayOnce)
        this.p.energy.use(EnergyPanel.totalCount); 
    } 
    this.isDone = true;
  }
}
package theSenseless.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import theSenseless.actions.WiltAction;

import theSenseless.SenselessMod;
import theSenseless.characters.TheSenseless;

import static theSenseless.SenselessMod.makeCardPath;

public class CardCurtail extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = SenselessMod.makeID("CardCurtail"); //DefaultCommonSkill.class.getSimpleName()
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheSenseless.Enums.COLOR_GRAY;

    private static final int COST = 1;
    
    private static final int CARD_DRAW = 1;
    private static final int UPGRADE_PLUS_CARD_DRAW = 1;

    private static final int DISCARD_CARD = 1;



    // /STAT DECLARATION/


    public CardCurtail() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CARD_DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, DISCARD_CARD, false));
        AbstractDungeon.actionManager.addToBottom(new WiltAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            upgradeMagicNumber(UPGRADE_PLUS_CARD_DRAW);

            initializeDescription();
        }
    }
}    
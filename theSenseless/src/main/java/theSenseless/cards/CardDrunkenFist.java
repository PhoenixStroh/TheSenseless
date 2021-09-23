package theSenseless.cards;

import theSenseless.actions.DrunkenFistAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;


import theSenseless.SenselessMod;
import theSenseless.characters.TheSenseless;
import theSenseless.powers.DrunkPower;

import static theSenseless.SenselessMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class CardDrunkenFist extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SenselessMod.makeID("CardDrunkenFist"); //DefaultCommonSkill.class.getSimpleName()
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheSenseless.Enums.COLOR_GRAY;

    private static final int COST = 1;  // COST = ${COST}

    private static final int CARD_DRAW = 1;
    private static final int UPGRADE_PLUS_CARD_DRAW = 1;

    private static final int DAMAGE = 9;    // DAMAGE = ${DAMAGE}

    // /STAT DECLARATION/


    public CardDrunkenFist() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = CARD_DRAW;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrunkenFistAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
      }
      
      public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
          if (AbstractDungeon.player.hasPower(DrunkPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
          } 
        } 


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_CARD_DRAW);
            initializeDescription();
        }
    }
}

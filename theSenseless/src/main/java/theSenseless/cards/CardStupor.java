package theSenseless.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSenseless.powers.DrunkPower;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType.*;

import theSenseless.SenselessMod;
import theSenseless.characters.TheSenseless;

import static theSenseless.SenselessMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class CardStupor extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = SenselessMod.makeID("CardStupor"); //DefaultCommonSkill.class.getSimpleName()
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheSenseless.Enums.COLOR_GRAY;

    private static final int COST = 1;  // COST = ${COST}

    private static final int DRUNK = 1;
    private static final int UPGRADE_DRUNK = 1;

    private static final int DAMAGE = 9;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}

    // /STAT DECLARATION/


    public CardStupor() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = DRUNK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(randomMonster, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new DrunkPower(p, p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_DRUNK);

            initializeDescription();
        }
    }
}

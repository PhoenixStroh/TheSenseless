package theSenseless.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSenseless.SenselessMod;
import theSenseless.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static theSenseless.SenselessMod.makeRelicOutlinePath;
import static theSenseless.SenselessMod.makeRelicPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScrollOfReflectionRelic extends CustomRelic {
    public static final Logger logger = LogManager.getLogger(SenselessMod.class.getName());

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = SenselessMod.makeID("ScrollOfReflectionRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    // private CardGroup tmpDeck;

    public ScrollOfReflectionRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    // @Override
    // public void atBattleStart(){
    //     tmpDeck = AbstractDungeon.player.discardPile;
    // }

    // @Override
    // public void onDrawOrDiscard()
    // {
    //     tmpDeck = AbstractDungeon.player.discardPile;
    // }

    // @Override
    // public void onPlayCard(AbstractCard c, AbstractMonster m) {
    //     AbstractPlayer p = AbstractDungeon.player;
    //     logger.info("Is Player null?: " + p + " Is Card null?: " + c);
    //     if (tmpDeck.contains(c))
    //     {
    //         logger.info("Played Card was in discard pile");   
    //     }
    //     else
    //     {
    //         logger.info("Played Card was not in discard pile");  
    //     }
    // }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

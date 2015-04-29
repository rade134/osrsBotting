package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Magic;

import java.util.concurrent.Callable;
import org.powerbot.script.rt4.Magic.Spell;

/**
 * Created by Jayden on 4/29/2015.
 */
public class CastSpell extends Task<ClientContext> {

    Callable<Boolean> cond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return true;
        }
    };
    Spell spell;
    public CastSpell (ClientContext ctx,Spell spell) {
        super(ctx);
        this.spell = spell;
    }
    public CastSpell (ClientContext ctx,Spell spell, Callable<Boolean> cond) {
        super(ctx);
        this.spell = spell;
        this.cond = cond;
    }
    public static class Runes {
        public static final int FIRE = 554;
        public static final int WATER = 555;
        public static final int EARTH = 557;
        public static final int LAW = 563;
        public static final int BODY = 559;
        public static final int AIR = 556;
        public static final int DEATH = 560;

    }
    public boolean activate () throws Exception {
        Callable<Boolean> whatRunes = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        };
        if (spell != Spell.VARROCK_TELEPORT) {
            whatRunes = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.select().id(Runes.FIRE).count(true) > 0 &&
                            ctx.inventory.select().id(Runes.LAW).count(true) > 0 &&
                            ctx.inventory.select().id(Runes.AIR).count(true) > 2;
                }
            };
        }
        if (spell != Spell.LUMBRIDGE_TELEPORT) {
            whatRunes = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.inventory.select().id(Runes.EARTH).count(true) > 0 &&
                            ctx.inventory.select().id(Runes.LAW).count(true) > 0 &&
                            ctx.inventory.select().id(Runes.AIR).count(true) > 2;
                }
            };
        }

        return cond.call() && whatRunes.call();
    }
    public void execute () {
        ctx.magic.cast(spell);
    }
}

package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Magic;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/29/2015.
 */
public class NoFeathersJob extends Task<ClientContext>{
    Callable<Boolean> cond;
    Area grandExchangeArea;
    Area bankArea;
    private int feathers = 314;
    private int[] varrRunes = new int[] {CastSpell.Runes.FIRE, CastSpell.Runes.AIR, CastSpell.Runes.LAW,CastSpell.Runes.EARTH};

    public NoFeathersJob(ClientContext ctx, Callable<Boolean> cond, Area grandExchangeArea, Area bankArea) {
        super(ctx);
        this.cond = cond;
        this.grandExchangeArea = grandExchangeArea;
        this.bankArea = bankArea;
        addTasks();
    }
    Callable<Boolean> walkGE = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.id(995).count() > 0 && !bankArea.contains(ctx.players.local());
        }
    };
    Callable<Boolean> walkBank = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.select().id(995).count() == 0;
        }
    };
    Callable<Boolean> castVarrock = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return bankArea.contains(ctx.players.local());
        }
    };
    Callable<Boolean> castVarrockFinal = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return bankArea.contains(ctx.players.local()) && ctx.inventory.select().id(995).count() != 0;
        }
    };
    Callable<Boolean> castLumb = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return grandExchangeArea.contains(ctx.players.local());
        }
    };
    Callable<Boolean> castLumbClose = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return !grandExchangeArea.contains(ctx.players.local());
        }
    };
    Callable<Boolean> castLumbFinal = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return grandExchangeArea.contains(ctx.players.local()) && ctx.inventory.select().id(feathers).count() != 0;
        }
    };
    public void addTasks() {
        taskList.addAll(Arrays.asList(new WalkLumbyBank(ctx,walkBank),
                new OpenBank(ctx,castVarrock),
                new WithdrawAllItem(ctx,varrRunes,1,castVarrock),
                new WithdrawAllItem(ctx,varrRunes[1],5,castVarrock),
                new WithdrawAllItem(ctx,995,53000,castVarrock),
                new CloseBank(ctx,walkBank),
                new CastSpell(ctx, Magic.Spell.VARROCK_TELEPORT,castVarrockFinal),
                new Walk(ctx,grandExchangeArea,walkGE),
                new BuyGE(ctx,walkBank,"feather",feathers),
                new CastSpell(ctx, Magic.Spell.LUMBRIDGE_TELEPORT,castLumbFinal)
        ));
    }
    public boolean activate() throws Exception {
        return cond.call();
    }
}

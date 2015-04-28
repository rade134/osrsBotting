package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.Condition;
import java.util.concurrent.Callable;
import org.powerbot.script.Tile;


public class Banking extends Task<ClientContext> {
    private int[] stairs = {16671,16672,16673};
    private int[] bankBooths = {18491,3227};
    private int[] depIds = {331,335};
    private enum State {
        walkCastle,
        upStairs1,
        upStairs2,
        walkBank,
        bank,
        downStairs1,
        downStairs2,
        walkFish,
        finished
    }
    State s = State.walkCastle;

    public Banking(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        if (s == State.finished) s = State.walkCastle;
        return ctx.inventory.select().count() == 28 || busy();
    }
    public boolean deposit(int[] ids) {
        boolean retValue = false;
        for (int id : ids) {
            retValue = ctx.bank.deposit(id, Amount.ALL);
        }
        return retValue;
    }
    private GameObject getClosest(int ids) {
        ctx.objects.select().id(ids);
        GameObject obj = ctx.objects.nearest().poll();
        return obj;
    }
    private GameObject getClosest(int[] ids) {
        ctx.objects.select().id(ids);
        GameObject obj = ctx.objects.nearest().poll();
        return obj;
    }
    public boolean busy() {
        return s != State.walkCastle;
    }
    public void execute() {
        Tile[] bankTile = {new Tile(3206,3209),new Tile(3205,3209,2),new Tile(3208,3220,2)};
        Tile fishTile = new Tile(3240,3242);

        if (s == State.walkCastle) {
            ctx.movement.step(bankTile[0]);
            if (ctx.players.local().tile().equals(bankTile[0])) s = State.upStairs1;
        }
        if (s == State.upStairs1) {
            if (getClosest(stairs).interact(false,"Climb-up")) {
                s = State.upStairs2;
            }
        }
        if (s == State.upStairs2) {
            if (getClosest(stairs[1]).interact(false,"Climb-up")) {
                s = State.bank;
            }
        }
        if (s == State.bank) {
            if (ctx.players.local().tile().equals(bankTile[2])) {

                if (Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        ctx.objects.select().id(bankBooths);
                        GameObject bankNPC = ctx.objects.nearest().poll();
                        return bankNPC.interact(false,"Bank");
                    }
                })) {
                    //open bank
                    if (Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return ctx.bank.opened();
                        }
                    })) {
                        //deposit
                        if (Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                return deposit(depIds);
                            }
                        })) {
                            //close
                            if (Condition.wait(new Callable<Boolean>() {
                                @Override
                                public Boolean call() {
                                    return ctx.bank.close();
                                }
                            })) {
                                ctx.movement.step(bankTile[1]);
                                s = State.downStairs1;
                            }
                        }
                    }
                }
            }else{
                ctx.movement.step(bankTile[2]);
            }

        }
        if (s == State.downStairs1 && ctx.players.local().tile().equals(bankTile[1])) {
            if (getClosest(stairs[2]).interact(false,"Climb-down")) {
                s = State.downStairs2;
            }
        }
        if (s == State.downStairs2) {
            if (getClosest(stairs[1]).interact(false,"Climb-down")) {
                s = State.walkFish;
            }
        }
        if (s == State.walkFish) {
            ctx.movement.step(fishTile);
            if (ctx.players.local().tile().equals(fishTile)) {
                s = State.finished;
            }
        }
    }
}

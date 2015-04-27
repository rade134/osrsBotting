package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import java.util.concurrent.Callable;
import org.powerbot.script.Tile;


/* defaults to banking at lumbridge castle and salmon and
trout deposits when it has a condition of full inventory */
public class BankRequest extends Task<ClientContext> {

    private int[] bankBooths = {18491,3227};
    private int[] depIds = {331,335};
    public Area bankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));
    Callable<Boolean> cond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.select().count() == 28;
        }
    };

    public BankRequest (ClientContext ctx) { super(ctx); }
    public BankRequest (ClientContext ctx, int[] depIds,Area bankArea, Callable<Boolean> cond) {
        super(ctx);
        this.depIds = depIds;
        this.bankArea = bankArea;
        this.cond = cond;
    }
        public BankRequest (ClientContext ctx, int[] depIds,Area bankArea) {
            super(ctx);
            this.depIds = depIds;
        this.bankArea = bankArea;
    }
    public BankRequest (ClientContext ctx, int[] depIds) {
        super(ctx);
        this.depIds = depIds;
    }

    public boolean activate() throws Exception {
        return bankArea.contains(ctx.players.local()) && cond.call();
    }

    public boolean deposit(int[] ids) {
        boolean retValue = false;
        for (int id : ids) {
            retValue = ctx.bank.deposit(id, Amount.ALL);
        }
        return retValue;
    }

    public void execute() {
        System.out.println("i am banking");

        if (Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                ctx.objects.select().id(bankBooths);
                GameObject bankNPC = ctx.objects.nearest().poll();
                ctx.camera.turnTo(bankNPC);
                return bankNPC.interact(false, "Bank");
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
                                ctx.bank.close();
                            }
                    }
            }

    }


}

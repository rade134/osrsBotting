package fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/27/2015.
 */

/* Default: opens bank if there is salmon or trout in your inventory
*  and you are in the bank area.*/
public class openBank extends Task<ClientContext> {

    private int[] boothIds = {18491,3227};

    private int[] ids = {331,335};

    private Callable<Boolean> cond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.id(ids).count() != 0;
        }
    };

    public openBank (ClientContext ctx) {
        super(ctx);
    }

    /*opens the bank under a specific condition
    or if you're in a specific bankArea */
    public openBank (ClientContext ctx, Callable<Boolean> cond) {
        super(ctx);
        this.cond = cond;

    }

    /*opens the bank if you have specific item ids in your invent
        or you're in a specific bankArea */
    public openBank (ClientContext ctx, int[] ids) {
        super(ctx);
        this.ids = ids;

    }
    public boolean activate() throws Exception {
        return cond.call() && !ctx.bank.opened();
    }
    public void execute() {
        ctx.objects.select().id(boothIds);
        GameObject bankNPC = ctx.objects.nearest().poll();
        ctx.camera.turnTo(bankNPC);
        if (bankNPC.interact(false, "Bank")) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.bank.opened();
                }
            });
        }
    }

}

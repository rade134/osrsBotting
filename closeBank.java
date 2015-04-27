package fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/27/2015.
 */
public class closeBank extends Task<ClientContext> {

    private int[] ids = {331,335};

    private Callable<Boolean> cond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.id(ids).count() != 0;
        }
    };

    public closeBank (ClientContext ctx) {
        super( ctx );
    }

    public closeBank (ClientContext ctx, Callable<Boolean> cond) {
        super( ctx );
        this.cond = cond;
    }

    public closeBank (ClientContext ctx, int[] ids) {
        super( ctx );
        this.ids = ids;
    }

    public boolean activate() throws Exception{
        return ctx.bank.opened() && !cond.call();
    }

    public void execute() {
        if (ctx.bank.close()) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.bank.opened();
                }
            });
        }
    }
}

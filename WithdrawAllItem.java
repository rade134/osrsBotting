package fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;


/*
 Will deposit a list of items,
 if no items specified will deposit entire inventory
 */
public class WithdrawAllItem extends Task<ClientContext> {
    private int[] ids;
    private Integer amount;

    public WithdrawAllItem(ClientContext ctx) {
        super (ctx);
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids) {
        super (ctx);
        this.ids = ids;
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids, int amount) {
        super (ctx);
        this.ids = ids;
        this.amount = amount;
    }

    public boolean activate() {
            return ctx.inventory.select().count() < 28 && ctx.bank.opened() && ctx.bank.id(ids).count() != 0;
    }
    public void execute() {

        for (final int id : ids) {
            final int lastAmount = ctx.inventory.id(id).count();
            if (amount != null) {
                if (ctx.bank.withdraw(id, amount)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return lastAmount < ctx.inventory.id(id).count();
                        }
                    });
                }
            }else {
                if (ctx.bank.withdraw(id, Amount.ALL)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return lastAmount < ctx.inventory.id(id).count();
                        }
                    });
                }
            }
        }
    }
}

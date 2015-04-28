package fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;


/*
 Will deposit a list of items,
 if no items specified will deposit entire inventory
 */
public class DepositAllItem extends Task<ClientContext> {
    private int[] ids;
    private Integer amount;








    public DepositAllItem(ClientContext ctx) {
        super (ctx);
    }
    public DepositAllItem(ClientContext ctx, int[] ids) {
        super (ctx);
        this.ids = ids;
    }
    public DepositAllItem(ClientContext ctx, int[] ids, int amount) {
        super (ctx);
        this.ids = ids;
        this.amount = amount;
    }

    public boolean activate() {
        if (ids == null) {
            return !ctx.inventory.isEmpty() && ctx.bank.opened();
        }else {
            return ctx.inventory.id(ids).count() != 0 && ctx.bank.opened();
        }

    }
    public void execute() {
        if (ids != null) {
            for (final int id : ids) {
                final int lastAmount = ctx.inventory.id(id).count();
                if (amount != null) {
                    if (ctx.bank.deposit(id, amount)) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                return ctx.inventory.id(id).count() == (lastAmount - amount);
                            }
                        });
                    }
                }else {
                    if (ctx.bank.deposit(id, Amount.ALL)) {
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                return ctx.inventory.id(id).count() == 0;
                            }
                        });
                    }
                }
            }
        }else{
            if (ctx.bank.depositInventory()) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return ctx.inventory.isEmpty();
                    }
                });
            }
        }
    }
}

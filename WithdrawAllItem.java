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
    private Callable<Boolean> cond;

    public WithdrawAllItem(ClientContext ctx) {
        super (ctx);
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids) {
        super (ctx);
        this.ids = ids;
    }
    public WithdrawAllItem(ClientContext ctx, int ids) {
        super (ctx);
        this.ids = new int[]  {ids};
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids, Callable<Boolean> cond) {
        super (ctx);
        this.ids = ids;
        this.cond = cond;
    }
    public WithdrawAllItem(ClientContext ctx, int ids, Callable<Boolean> cond) {
        super (ctx);
        this.ids = new int[]  {ids};
        this.cond = cond;
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids, int amount) {
        super (ctx);
        this.ids = ids;
        this.amount = amount;
    }
    public WithdrawAllItem(ClientContext ctx, int ids, int amount) {
        super (ctx);
        this.ids = new int[] {ids};
        this.amount = amount;
    }
    public WithdrawAllItem(ClientContext ctx, int[] ids, int amount, Callable<Boolean> cond) {
        super (ctx);
        this.ids = ids;
        this.amount = amount;
        this.cond = cond;
    }
    public WithdrawAllItem(ClientContext ctx, int ids, int amount, Callable<Boolean> cond) {
        super (ctx);
        this.ids = new int[] {ids};
        this.amount = amount;
        this.cond = cond;
    }

    public boolean activate() throws Exception {

        boolean test = ctx.inventory.select().count() < 28 && ctx.bank.opened() && cond.call();
            System.out.println("id "+ ids[0]);
            System.out.println("bank open: " + ctx.bank.opened());
            System.out.println("id count: " + ctx.bank.id(ids).count());
            System.out.println("cond.call() : " + cond.call());
            return test;
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

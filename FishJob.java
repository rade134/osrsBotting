package fishing;

import org.powerbot.script.rt4.ClientContext;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/29/2015.
 */
public class FishJob extends Task<ClientContext> {
    Callable<Boolean> cond;
    boolean drop;
    public FishJob(ClientContext ctx, Callable<Boolean> cond,boolean drop) {
        super(ctx);
        this.cond = cond;
        this.drop = drop;
        addTasks();
    }
    private void addTasks() {
        if (drop) {
            taskList.addAll(Arrays.asList(new WalkLumbyFish(ctx),
                    new Drop(ctx),
                    new Fish(ctx)
            ));
        }else{
            taskList.addAll(Arrays.asList(new WalkLumbyBank(ctx),
                    new BankItemsJob(ctx),
                    new WalkLumbyFish(ctx),
                    new Fish(ctx)
            ));
        }
    }
    public boolean activate() throws Exception{
        return !cond.call();
    }
}

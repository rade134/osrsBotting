package fishing;

import org.powerbot.script.rt4.ClientContext;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/29/2015.
 */
public class FishJob extends Task<ClientContext> {
    Callable<Boolean> cond;
    public FishJob(ClientContext ctx, Callable<Boolean> cond) {
        super(ctx);
        this.cond = cond;
        addTasks();
    }
    private void addTasks() {
        taskList.addAll(Arrays.asList(new WalkLumbyBank(ctx),
                new WalkLumbyFish(ctx),
                new BankItemsJob(ctx),
                new Fish(ctx)
        ));
    }
    public boolean activate() throws Exception{
        return !cond.call();
    }
}

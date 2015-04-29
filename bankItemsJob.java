package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Magic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/27/2015.
 */
public class BankItemsJob extends Task<ClientContext> {

    private int[] ids = {331,335};
    private int feathers = 314;
    private int[] runes = {563,556,554};
    public Area bankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));

    /* was going to swap over tasks in case of grand exchange
    protected List<Task> fishingTasks = new ArrayList<Task>();
    protected List<Task> featherTasks = new ArrayList<Task>();
    */


    private Callable<Boolean> cond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.id(ids).count() != 0;
        }
    };


    private void addTasks () {
        taskList.addAll(Arrays.asList(
                new OpenBank(ctx,cond),
                new DepositAllItem(ctx,ids),
                new CloseBank(ctx,cond)));
    }
    public BankItemsJob(ClientContext ctx) {
        super( ctx );
        addTasks();
    }
    public BankItemsJob(ClientContext ctx, Area bankArea) {
        super(ctx);
        this.bankArea = bankArea;
        addTasks();
    }
    public BankItemsJob(ClientContext ctx, Area bankArea, int[] ids) {
        super(ctx);
        this.bankArea = bankArea;
        addTasks();
        this.ids = ids;
    }
    public BankItemsJob(ClientContext ctx, int[] ids) {
        super(ctx);
        addTasks();
        this.ids = ids;
    }

    public boolean activate() {
        System.out.println("Bank Items Job: " + bankArea.contains(ctx.players.local()));
        return bankArea.contains(ctx.players.local());
    }

}

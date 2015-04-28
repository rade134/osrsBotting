package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jayden on 4/27/2015.
 */
public class BankItemsJob extends Task<ClientContext> {

    private int[] ids = {331,335};
    public Area bankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));

    private void addTasks () {
        taskList.addAll(Arrays.asList(new OpenBank(ctx), new DepositAllItem(ctx,ids), new CloseBank(ctx)));
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
        return bankArea.contains(ctx.players.local());
    }

}

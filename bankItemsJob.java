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
public class bankItemsJob extends Task<ClientContext> {

    private int[] ids = {331,335};
    private List<Task> taskList = new ArrayList<Task>();
    public Area bankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));

    private void addTasks () {
        taskList.addAll(Arrays.asList(new openBank(ctx), new depositAllItem(ctx,ids), new closeBank(ctx)));
    }
    public bankItemsJob (ClientContext ctx) {
        super( ctx );
        addTasks();
    }
    public bankItemsJob (ClientContext ctx, Area bankArea) {
        super(ctx);
        this.bankArea = bankArea;
        addTasks();
    }
    public bankItemsJob (ClientContext ctx, Area bankArea, int[] ids) {
        super(ctx);
        this.bankArea = bankArea;
        addTasks();
        this.ids = ids;
    }
    public bankItemsJob (ClientContext ctx,  int[] ids) {
        super(ctx);
        addTasks();
        this.ids = ids;
    }

    public boolean activate() {
        return bankArea.contains(ctx.players.local());
    }

    public void execute() {
        for (Task task : taskList) {
            try {
                if (task.activate()) {
                    task.execute();
                }
            } catch (Exception e) {
                methodClass.printErrors(e);
            }
        }
    }

}

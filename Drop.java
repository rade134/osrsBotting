package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;

public class Drop extends Task<ClientContext> {
    private int[] logId = {331,335};
    public Drop(ClientContext ctx) {
        super(ctx);
    }
    public boolean activate() {
        return ctx.inventory.select().count() == 28;
    }

    public void execute() {
        for (Item i : ctx.inventory.id(logId)) {
            i.interact("Drop");
        }
    }
}

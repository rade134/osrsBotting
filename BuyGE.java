package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/29/2015.
 */
public class BuyGE extends Task<ClientContext>{
    int[] geClerk = new int[] {2148,2149,2150,2151};
    public Area grandExchangeArea = new Area( new Tile(3160,3483), new Tile(3170,3493));
    Callable<Boolean> cond;
    String itemName;
    int itemID;

    GrandExchange newGE = new GrandExchange(ctx);
    public BuyGE (ClientContext ctx, Callable<Boolean> cond,String itemName, int itemID) {
        super(ctx);
        this.cond = cond;
        this.itemName = itemName;
        this.itemID = itemID;
    }
    public boolean activate() throws Exception {
        return grandExchangeArea.contains(ctx.players.local()) && cond.call();
    }
    public void execute() {
        final Npc closestClerk = ctx.npcs.select().id(geClerk).nearest().poll();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return closestClerk.interact("Exchange");
            }
        });
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    return newGE.buyItem(itemName, 13000, 4);
                } catch (Exception e) {
                    MethodClass.printErrors(e);
                    return false;
                }
            }
        });
        newGE.close();




    }

}

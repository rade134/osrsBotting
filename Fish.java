package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Npc;

public class Fish extends Task<ClientContext> {
    public Area lumbyFishArea = new Area(new Tile(3235,3234,0),new Tile(3248,3257,0));
    private int[] spotIds = {1527};

    public Fish(ClientContext ctx) {
        super(ctx);
    }
    public boolean activate() {
        return ctx.inventory.select().count() < 28
                && !ctx.npcs.select().id(spotIds).isEmpty()
                && ctx.players.local().animation() == -1
                && lumbyFishArea.contains(ctx.players.local());
    }

    public void execute() {





        System.out.println("i am fishing");
        Npc fishingSpot = ctx.npcs.nearest().poll();
        if (fishingSpot.inViewport()) {
            fishingSpot.interact("Lure");
        } else {
            ctx.movement.step(fishingSpot);
            ctx.camera.turnTo(fishingSpot);
        }
    }
}

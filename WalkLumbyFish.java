package fishing;

import org.powerbot.script.Area;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Tile;

public class WalkLumbyFish extends Walk {
    private int[] stairs = {16672,16673};
    public Area lumbyFishArea = new Area(new Tile(3235,3234,0),new Tile(3248,3257,0));

    public WalkLumbyFish(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if (s == State.finished) s = State.walk1;

        if (ctx.inventory.select().count() < 28 && !lumbyFishArea.contains(ctx.players.local())) {
            return true;
        }else{
            s = State.walk1;
            return false;
        }
    }

    @Override
    public void execute() {
        System.out.println("I am walking to lumbridge fishing spot");
        System.out.println("my state is: " + s);
        Tile bankTile = new Tile(3205,3209,2);
        Tile fishTile = new Tile(3240,3242);
        run();
        if (ctx.players.local().tile().floor() == 0) {
            s = State.walk2;
        }

        //walk to stairs
        if (s == State.walk1) {
            ctx.camera.turnTo(bankTile);
            ctx.movement.step(bankTile);
            if (ctx.players.local().tile().equals(bankTile)) s = State.stairs1;
        }
        //descend first set of stairs
        if (s == State.stairs1) {
            if (getClosest(stairs).interact(false,"Climb-down")) {
                s = State.stairs2;
            }
        }
        //descend second set of stairs
        if (s == State.stairs2) {
            if (getClosest(stairs[0]).interact(false,"Climb-down")) {
                s = State.walk2;
            }
        }
        //walk to lumb fish spot
        if (s == State.walk2) {
            ctx.camera.turnTo(fishTile);
            ctx.movement.step(fishTile);
            if (lumbyFishArea.contains(ctx.players.local())) s = State.finished;
        }
    }
}

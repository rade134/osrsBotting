package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.Tile;
import org.powerbot.script.Area;

public class WalkLumbyBank extends Walk {
    private int[] stairs = {16671,16672};
    public Area lumbyBankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));

    public WalkLumbyBank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        if (s == State.finished) s = State.walk1;

        if (ctx.inventory.select().count() == 28 && !lumbyBankArea.contains(ctx.players.local())) {
            return true;
        }else{
            s = State.walk1;
            return false;
        }
    }

    @Override
    public void execute() {
        System.out.println("i am walking to the bank");
        System.out.println("my state is: "+s);
        Tile[] bankTile = {new Tile(3206,3209),new Tile(3208,3220,2)};
        run();

        //walk to castle
        if (s == State.walk1) {
            ctx.camera.turnTo(bankTile[0]);
            ctx.movement.step(bankTile[0]);
            if (ctx.players.local().tile().equals(bankTile[0])) s = State.stairs1;
        }
        //climb first set of stairs
        if (s == State.stairs1) {
            if (getClosest(stairs).interact(false,"Climb-up")) {
                s = State.stairs2;
            }
        }
        //climb second set of stairs
        if (s == State.stairs2) {
            if (getClosest(stairs[1]).interact(false,"Climb-up") ) {
                s = State.walk2;
            }
        }
        //walk to bank
        if (s == State.walk2) {
            ctx.camera.turnTo(bankTile[1]);
            ctx.movement.step(bankTile[1]);
            if (lumbyBankArea.contains(ctx.players.local())) s = State.finished;
        }
    }
}

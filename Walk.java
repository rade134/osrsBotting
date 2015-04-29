package fishing;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.Area;

import java.util.concurrent.Callable;

public class Walk extends Task<ClientContext> {
    private Area areaToArrive;
    Callable<Boolean> cond;

    public Walk(ClientContext ctx) {
        super(ctx);
    }
    public Walk(ClientContext ctx, Area areaToArrive, Callable<Boolean> cond ) {
        super(ctx);
        this.areaToArrive = areaToArrive;
        this.cond = cond;
    }

    protected enum State {
        walk1,
        stairs1,
        stairs2,
        walk2,
        finished
    }
    protected State s = State.walk1;

    public boolean activate() throws Exception{
        if (areaToArrive == null) {
            return true;
        }else{
            return !areaToArrive.contains(ctx.players.local()) && cond.call();
        }
    }

    protected GameObject getClosest(int ids) {
        ctx.objects.select().id(ids);
        GameObject obj = ctx.objects.nearest().poll();
        ctx.camera.turnTo(obj);
        return obj;
    }
    protected GameObject getClosest(int[] ids) {
        ctx.objects.select().id(ids);
        GameObject obj = ctx.objects.nearest().poll();
        ctx.camera.turnTo(obj);
        return obj;
    }
    protected void run() {
        if (ctx.movement.energyLevel() > 20) {
            ctx.movement.running(true);
        }
    }

    public void execute(){
        System.out.println("Walking");
        if (areaToArrive != null) {
            ctx.movement.step(areaToArrive.getClosestTo(ctx.players.local()));
            run();
        }
    }
}

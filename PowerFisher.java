package fishing;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Script.Manifest(name = "fish'n'bank", description = "fish trout and salmon and drops them", properties="client=4")
public class PowerFisher extends PollingScript<ClientContext> implements PaintListener {


    private List<Task> taskList = new ArrayList<Task>();
    private DrawBottingSummary drawSummary;
    GrandExchange newGE = new GrandExchange(ctx);

    private int feathers = 314;
    public Area grandExchangeArea = new Area( new Tile(3160,3483), new Tile(3170,3493));
    public Area bankArea = new Area(new Tile(3205,3211,2),new Tile(3212,3225,2));

    private Callable<Boolean> buyFeathersCond = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            return ctx.inventory.select().id(feathers).count() == 0 || grandExchangeArea.contains(ctx.players.local());
        }
    };


    @Override
    public void start() {
        /*
        taskList.addAll(Arrays.asList(new WalkLumbyBank(ctx),
                new WalkLumbyFish(ctx),
                new BankItemsJob(ctx),
                new Fish(ctx),
                new Walk(ctx,grandExchangeArea,buyFeathersCond)
        ));*/
        // run startup GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartupGUI g = new StartupGUI();
                g.setVisible(true);
            }
        });
        taskList.addAll(Arrays.asList(new FishJob(ctx,buyFeathersCond,true), new NoFeathersJob(ctx,buyFeathersCond,grandExchangeArea,bankArea)));

        try {
            drawSummary = new DrawBottingSummary(ctx);
        } catch (Exception e) {
            MethodClass.printErrors(e);
        }
    }

    @Override
    public void poll() {


        for (Task task : taskList) {
            try {
                if (task.activate()) {
                    task.execute();
                }
            } catch (Exception e) {
                MethodClass.printErrors(e);
        }
        }
    }

    @Override
    public void repaint(Graphics g) {
        try {
            drawSummary.repaint(g);
        } catch (Exception e) {
            MethodClass.printErrors(e);
        }
    }

}

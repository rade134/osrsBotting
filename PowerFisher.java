package fishing;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "fish'n'bank", description = "fish trout and salmon and drops them", properties="client=4")
public class PowerFisher extends PollingScript<ClientContext> implements PaintListener {

    private List<Task> taskList = new ArrayList<Task>();
    private DrawPaint paintBox = new DrawPaint();

    @Override
    public void start() {
        taskList.addAll(Arrays.asList(new WalkLumbyBank(ctx),new WalkLumbyFish(ctx),new BankItemsJob(ctx),new Fish(ctx)));
        paintBox.addText("say hi for me !!");
        paintBox.addText("please");
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
        paintBox.repaint(g);
    }

}

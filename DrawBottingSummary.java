package fishing;

import com.sun.deploy.util.SessionState;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Skills;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/28/2015.
 */
public class DrawBottingSummary {

    ClientContext ctx;
    int curLevel;
    int expToLevel;
    Callable<String> getCurLevel = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Current Level: " + curLevel;
        }
    };
    Callable<String> getExpToLevel = new Callable<String>() {
        @Override
        public String call() throws Exception {

            return "Experience To Level: " + expToLevel;
        }
    };

    private DrawPaint newPaint = new DrawPaint(10,10);
    public DrawBottingSummary(ClientContext ctx) throws Exception {
        this.ctx = ctx;
        newPaint.addText(getCurLevel);
        newPaint.addText(getExpToLevel);
    }
    public void repaint (Graphics g) throws Exception {
        newPaint.repaint(g);
        updateSummary();
    }
    public void updateSummary() {
        curLevel = ctx.skills.realLevel(Skills.FISHING);
        expToLevel = (experienceAtLevel(ctx.skills.realLevel(Skills.FISHING))-ctx.skills.experience(Skills.FISHING));
    }
    public int experienceAtLevel(int L) {
        double sum = 0;
        for (double x = 1; x <= L ; x++ ) {
            sum += Math.floor(x+ 300 * Math.pow(2,x/7));
        }
        return (int) Math.floor(sum/4);
    }
}

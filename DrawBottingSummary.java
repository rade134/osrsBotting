package fishing;

import com.sun.deploy.util.SessionState;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Skills;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/28/2015.
 */
public class DrawBottingSummary {

    ClientContext ctx;
    int chosenSkill = Constants.SKILLS_FISHING;
    int curLevel;
    int expToLevel;
    double expPerHour;
    long startExp = 1;
    long startTime = 1;
    double timeTilLevel;

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
    Callable<String> getExpPerHour = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Experience Per Hour: " + (int) expPerHour;
        }
    };
    Callable<String> getTimeTilLevel = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Time Until Level: " + formatTime(timeTilLevel);
        }
    };
    Callable<String> getTimeRunning = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return "Time Running: " + formatTime((double)(System.currentTimeMillis()-startTime) / 3600000);
        }
    };

    public String formatTime (double time) {
        int hours = (int) Math.floor(time);
        int minutes = (int) Math.floor((time - hours) * 60);
        int seconds = (int) Math.floor(((time-hours)*60 - minutes ) * 60);
        String minut = Integer.toString( minutes);
        String second = Integer.toString(seconds);
        if (minutes < 10) minut = "0"+minutes;
        if (seconds < 10) second = "0"+seconds;
        return hours + ":"+ minut + ":" + second;
    }

    private DrawPaint newPaint = new DrawPaint(ctx,10,10);
    public DrawBottingSummary(ClientContext ctx) throws Exception {
        this.ctx = ctx;
        setupText();
    }
    public DrawBottingSummary(ClientContext ctx, int skill) throws Exception {
        this.ctx = ctx;
        this.chosenSkill = skill;
        setupText();
    }
    public void repaint (Graphics g) throws Exception {
        newPaint.repaint(g);
        updateSummary();
    }
    public void setupText() throws Exception{
        newPaint.addText(getCurLevel);
        newPaint.addText(getExpToLevel);
        newPaint.addText(getExpPerHour);
        newPaint.addText(getTimeTilLevel);
        newPaint.addText(getTimeRunning);
        startExp= ctx.skills.experience(chosenSkill);
        startTime = System.currentTimeMillis();
    }
    public void updateSummary() {
        //setting current level
        curLevel = ctx.skills.realLevel(chosenSkill);
        //setting experience to next level
        expToLevel = (experienceAtLevel(ctx.skills.realLevel(chosenSkill))-ctx.skills.experience(chosenSkill));
        //setting exp Per hour
        expPerHour = (double) (ctx.skills.experience(chosenSkill) - startExp)/(System.currentTimeMillis() - startTime) * 3600000;
        //setting time until next level
        if (expPerHour == 0 ) {
            timeTilLevel = 999999999;
        } else {
            timeTilLevel = (double) expToLevel/expPerHour;
        }

    }

    public int experienceAtLevel(int L) {
        double sum = 0;
        for (double x = 1; x <= L ; x++ ) {
            sum += Math.floor(x+ 300 * Math.pow(2,x/7));
        }
        return (int) Math.floor(sum/4);
    }

}

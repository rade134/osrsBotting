package fishing;

import org.powerbot.script.rt4.ClientContext;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

public class DrawPaint {

    private int x = 10;
    private int y = 10;
    private int length = 20;
    Font font = new Font("arial", Font.PLAIN, 12);
    ClientContext ctx;

    private List<Callable<String>> texts = new ArrayList<Callable<String>>();

    public DrawPaint(ClientContext ctx) {
        this.ctx = ctx;
    }
    public DrawPaint(ClientContext ctx, int x, int y) {
        this.x = x;
        this.y = y;
        this.ctx = ctx;
    }
    public DrawPaint(ClientContext ctx, int x, int y, Font font) {
        this.font = font;
        this.x = x;
        this.y = y;
        this.ctx = ctx;
    }

    public void addText(final String string) {
        Callable<String> stringReturn = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return string;
            }
        };
        texts.add(stringReturn);
        length = Math.max(length,string.length()*6 + 20);

    }
    public void addText(Callable<String> string) throws Exception {
        texts.add(string);
        length = Math.max(length,string.call().length()*6 + 20);
    }
    public void drawMouse(Graphics2D g) throws Exception{

        int x = (int) ctx.input.getLocation().getX();
        int y = (int) ctx.input.getLocation().getY();
        //draw red cursor
        g.drawLine(x,0,x,338);
        g.drawLine(0,y,516,y);
    }
    public void drawTextBox(Graphics2D g) throws Exception{
        int windowHeight = 20+ (texts.size()-1) * 20;

        //red rectangle
        Color c = new Color(255,0,0);
        g.setColor(c);
        g.fillRect(x, y,length, windowHeight);

        //white text
        c = new Color(255,255,255);
        g.setColor(c);
        g.setFont(font);

        for (int i=0; i < texts.size(); i++ ) {
            g.drawString(texts.get(i).call(),x+10,y+15+i*20);
        }
    }

    public void repaint(Graphics lastG) throws Exception {
        Graphics2D g = (Graphics2D) lastG;

        drawTextBox(g);
        drawMouse(g);
    }
}

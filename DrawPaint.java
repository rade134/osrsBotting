package fishing;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

public class DrawPaint {

    private int x = 10;
    private int y = 10;
    private int length = 20;
    Font font = new Font("arial", Font.PLAIN, 12);

    private List<Callable<String>> texts = new ArrayList<Callable<String>>();

    public DrawPaint() {

    }
    public DrawPaint(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void repaint(Graphics lastG) throws Exception {
        int windowHeight = 20+ (texts.size()-1) * 20;
        Graphics2D g = (Graphics2D) lastG;

        //red
        Color c = new Color(255,0,0);
        g.setColor(c);
        g.fillRect(x, y,length, windowHeight);

        c = new Color(255,255,255);
        g.setColor(c);
        g.setFont(font);
        /*
        for (String text : texts) {
            g.drawString(text,x+10,y+10+texts.indexOf(text)*20);
        }*/
        for (int i=0; i < texts.size(); i++ ) {
            g.drawString(texts.get(i).call(),x+10,y+15+i*20);
        }

    }
}

package fishing;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;

/**
 * Created by Jayden on 4/29/2015.
 */
public class GrandExchange {

    ClientContext ctx;
    Widget geWidget;
    Widget geSearchWidget;
    Widget invWidget;
    public GrandExchange(ClientContext ctx) {
        this.ctx = ctx;
        this.geWidget = ctx.widgets.widget(465);
        this.invWidget = ctx.widgets.widget(467);
        this.geSearchWidget = ctx.widgets.widget(548);

    }
    public boolean opened() {
        return geWidget.component(0).visible();
    }
    public Component getSlot() {
        for (int i=6; i<= 11; i++ ) {
            if (geWidget.component(i).visible()) {
                return geWidget.component(i);
            }
        }
        return null;
    }
    public boolean clickBuy() throws Exception {
        System.out.println("Clicks on Buy Button");
        if (buySellWindowOpen() || !opened()) throw new Exception("Close the buy/sell window!");
        Component openSlot = getSlot();
        return openSlot.component(0).click();
    }
    public boolean clickConfirm() throws Exception {
        System.out.println("Clicks on Confirm Button");
        if (!geWidget.component(24).visible()) throw new Exception("Select an Item first");
        return geWidget.component(24).click();
    }
    public boolean clickSell() throws Exception{
        System.out.println("Clicks on Sell Button");
        if (buySellWindowOpen() || !opened()) throw new Exception("Close the buy/sell window!");
        Component openSlot = getSlot();
        return openSlot.component(1).click();
    }
    public boolean typeGEInput(String input) throws Exception {
        System.out.println("Typed into the GE");
        if (!buySellWindowOpen() || !opened()) throw new Exception("Open the Buy Window First!");
        return ctx.input.sendln(input);
    }
    public boolean typeGEInput(int input) throws Exception {
        return typeGEInput(Integer.toString(input));
    }
    public boolean typeEnter() throws Exception{
        System.out.println("Pressed Enter");
        if (!buySellWindowOpen() || !opened()) throw new Exception("Open the Buy Window First!");
        ctx.input.press(13);
        return true;
    }
    public boolean offerItem(int invId) throws Exception {
        System.out.println("Offers item into ge");
        if (invId < 0 || invId > 27) throw new Exception("Enter the correct Inventory place");
        return invWidget.component(0).component(invId).click();
    }
    public boolean clickSearchItem() {
        System.out.println("Clicks the searched item");
        return geSearchWidget.component(125).component(0).click();
    }
    public boolean quantPriceField() {
        return geSearchWidget.component(118).visible();
    }
    public boolean clickQuantity() throws Exception{
        System.out.println("Clicks the quantity");
        if (!buySellWindowOpen() || !opened()) throw new Exception("Open the Buy Window First!");
        return geWidget.component(21).component(7).click();
    }
    public boolean clickPrice() throws Exception {
        System.out.println("Clicks the price");
        if (!buySellWindowOpen() || !opened()) throw new Exception("Open the Buy Window First!");
        return geWidget.component(21).component(12).click();
    }
    public boolean buyItem(final String name, final int quantity,final int price) throws Exception {
        boolean itemBought = true;
        //
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickBuy();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return buySellWindowOpen();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return typeGEInput(name);
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickSearchItem();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickQuantity();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return quantPriceField();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return typeGEInput(quantity);
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return typeEnter();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickPrice();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return quantPriceField();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return typeGEInput(price);
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return typeEnter();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickConfirm();
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickNonCompleted(name);
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickReceive(0);
            }
        });
        itemBought = itemBought && Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception{
                return clickReceive(1);
            }
        });


        return itemBought;
    }
    public boolean isItemCompleted(int id) throws Exception {
        if (id < 0 || id > 5) throw new Exception("Select a ge offer within 1-6");
        return geWidget.component(6+id).component(23).textColor() == 24320;

    }
    public boolean isItemOffer(String name) throws Exception {
        int i;
        for (i=0;i<6;i++) {
            System.out.println("name: "+geWidget.component(i+6).component(19).text());
            if (geWidget.component(i+6).component(19).text().toLowerCase().equals(name.toLowerCase())) break;
            if (i == 5) return false;
        }
        return true;
    }
    public int itemOfferId(String name) throws Exception {
        int i;
        for (i=0;i<6;i++) {
            System.out.println("name: "+geWidget.component(i+6).component(19).text());
            if (geWidget.component(i+6).component(19).text().toLowerCase().equals(name.toLowerCase())) break;
            if (i == 5) return 0;
        }
        return i;
    }
    public boolean clickOffer(int id) throws Exception {
        System.out.println("Clicks completed");
        if (!geWidget.component(6+id).component(2).visible()) throw new Exception("Click a valid tab");
        return geWidget.component(6+id).component(2).click();
    }
    public boolean clickNonCompleted(String name) throws Exception {
        System.out.println("Try's to find a completed");
        return clickOffer(itemOfferId(name));

    }
    public boolean clickCompleted(String name) throws Exception {
        if (isItemOffer(name)) {
            return clickNonCompleted(name);
        } else {
            return false;
        }
    }
    public boolean clickReceive(int i) throws Exception{
        System.out.println("Clicks Receive: "+i);
        if (!geWidget.component(20).visible()&& i >= 0 && i <= 1) throw new Exception("Click on a completed item first");
        return geWidget.component(20).component(i).click();
    }

    public boolean buySellWindowOpen() {
        return geWidget.component(21).visible();
    }

    public boolean close() {
        return geWidget.component(2).component(11).click();
    }


}

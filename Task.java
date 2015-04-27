package fishing;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;
import org.powerbot.script.Area;

import java.util.concurrent.Callable;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {
    public Task(C ctx) {
        super(ctx);
    }
    public abstract boolean activate() throws Exception;

    public abstract void execute();
}

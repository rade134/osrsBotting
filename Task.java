package fishing;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C> {
    public Task(C ctx) {
        super(ctx);
    }
    public abstract boolean activate() throws Exception;
    protected List<Task> taskList = new ArrayList<Task>();


    public void execute() {
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
}

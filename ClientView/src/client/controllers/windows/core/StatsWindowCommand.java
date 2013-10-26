package client.controllers.windows.core;

import client.controllers.ICommand;
import client.stages.StatWindow;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatsWindowCommand implements ICommand {
    @Override
    public void execute() {
        new StatWindow().show();
    }
}

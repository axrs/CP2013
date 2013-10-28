package client.controllers.windows.staff;

import client.controllers.ICommand;
import client.controllers.windows.core.NewStage;
import client.stages.staff.AboutStaff;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 28/10/13
 * Time: 9:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowAboutStaffCommand extends NewStage implements ICommand {
    @Override
    public void execute() {
        new AboutStaff();
    }
}

package client.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowRegisterCommand implements ICommand{
    @Override
    public void execute() {
         RegisterNewUserWindow r = new RegisterNewUserWindow();
        r.show();
    }
}

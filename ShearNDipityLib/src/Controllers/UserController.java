package Controllers;



import java.util.Date;

public class UserController {

    private static UserController instance = null;
    private Mutex UserLocker;
    private Date lastUpdate;

    /**
     * Singleton constructor.  Not able to be overridden
     */
    protected UserController() {
        if (UserLocker == null) {
            UserLocker = new Mutex();
            lastUpdate = new Date();
        }
        if ((new Date().getTime() - lastUpdate.getTime()) > 60000) {
        }
    }
}

package Controllers;


import Models.Config;
import Models.User;
import com.google.gson.Gson;
import dao.IUserDAO;


public class UserController implements IUserDAO {


    private static UserController instance = null;
    private User user;
    private int userId;
    private Mutex userLocker;

    @Override
    public void getCurrentUser() {
        user = User.getLoggedInUser();
    }

    @Override
    public void getUser(int id) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new GetUserResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/user/" + String.valueOf(id));
        Thread runnerThread = new Thread(runner, "Getting User");
        runnerThread.start();
    }

    @Override
    public void createUser(User user) {
        RESTRunner runner = new RESTRunner();
        runner.addListner(new ModifyContactResultListener());
        runner.setRequest(Config.getInstance().getServer() + "/api/user");
        runner.setMethod("Put");
        runner.setMessage(new Gson().toJson(user, User.class));
        Thread runnerThread = new Thread(runner, "Creating User");
        runnerThread.start();
    }

    @Override
    public void removeUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeUser(int id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void updateUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class ModifyContactResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the contact object
            ((RESTRunner) result.getSource()).removeListener(this);

            if (result.getStatus() != 201 && result.getStatus() != 202) return;
        }
    }

    private class GetUserResultListener implements RESTRunner.ResultsListener {
        @Override
        public void results(RESTRunner.Result result) {

            //Remove the listener from the user object
            ((RESTRunner) result.getSource()).removeListener(this);

            User u = null;
            if (result.getStatus() != 200) return;
            //Process results
            try {
                userLocker.acquire();
                try {
                    u = new Gson().fromJson(result.getResponse(), User.class);
                    user = u;
                } finally {
                    userLocker.release();
                }
            } catch (InterruptedException ie) {

            }
        }
    }
}

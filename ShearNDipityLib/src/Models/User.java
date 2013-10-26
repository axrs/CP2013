package Models;

public class User extends Contact {


    public static User loggedInUser = null;
    public static String userToken = "";

    private String token;
    private int userId;
    private int strategyId;
    private String strategy = "local";
    private String strategyData;
    private String userName;
    private int isAdmin;
    private String md5Email;


    public User(String contForename, String contSurname) {
        super(contForename, contSurname);
    }

    public User() {
    }


    public static void setUserToken(String userToken) {
        User.userToken = userToken;
    }

    public static String getAuthToken() {
        String result = "";
        if (loggedInUser != null) {
            loggedInUser.getToken();
        }
        return result;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategyData() {
        return strategyData;
    }

    public void setStrategyData(String strategyData) {
        this.strategyData = strategyData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAdmin() {
        return isAdmin;
    }

    public void setAdmin(int admin) {
        isAdmin = admin;
    }

    public String getMd5Email() {
        return md5Email;
    }

    public void setMd5Email(String md5Email) {
        this.md5Email = md5Email;
    }
}

package DAO;

public class DAO {
    private static DAO instance;

    protected DAO() {

    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    public static void setInstance(DAO instance) {
        DAO.instance = instance;
    }
}

/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class User extends Contact {

    protected String userName;
    protected String userPass;
    protected Boolean userIsAdmin;

    public User(int contID, String contFirstName, String contLastName, String contCompany, int contPhone, String contEmail, String contAddress, String userName, String userPass, Boolean userIsAdmin) {
        super(contID, contFirstName, contLastName, contCompany, contPhone, contEmail, contAddress);
        this.userName = userName;
        this.userPass = userPass;
        this.userIsAdmin = userIsAdmin;
    }
}

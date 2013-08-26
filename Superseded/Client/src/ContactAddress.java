/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 12/08/13
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactAddress {

    protected int contID;
    protected int addrID;
    protected int addrTypeID;
    protected boolean addrIsActive;

    public ContactAddress(int contID, int addrID, int addrTypeID, boolean addrIsActive) {
        this.contID = contID;
        this.addrID = addrID;
        this.addrTypeID = addrTypeID;
        this.addrIsActive = addrIsActive;
    }
}

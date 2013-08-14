/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 13/08/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Address {

    protected String addrStreet;
    protected String addrSuburb;
    protected String addrCity;
    protected int addrPostCode;
    protected String addrState;

    public Address(String addrStreet, String addrSuburb, String addrCity, int addrPostCode, String addrState) {
        this.addrStreet = addrStreet;
        this.addrSuburb = addrSuburb;
        this.addrCity = addrCity;
        this.addrPostCode = addrPostCode;
        this.addrState = addrState;
    }
}

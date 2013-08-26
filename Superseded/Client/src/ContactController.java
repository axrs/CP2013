import java.util.Date;
import java.util.HashMap;

import com.google.gson.*;


/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 22/08/13
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactController extends RestAPI {

    //private static container of Contacts

    private static Date _timeStamp;

    private static HashMap<Integer,Contact> _map;
    public ContactController() {
        super();

        if (_map == null){
            _map = new HashMap<Integer, Contact>();
        }

        if (_timeStamp == null ||
                (new Date().getTime() - _timeStamp.getTime()) > 60000
        /**|| containr.size == 0*/
                ) {
            getAllFromServer();
        }
    }

    public static void forceContactLoad(){
        getAllFromServer();
    }

    private static void getAllFromServer() {
        _timeStamp = new Date();
        RESTResult result = get("http://shear-n-dipity.com/api/contacts");

        _map = new HashMap<Integer, Contact>();

        System.out.println(result.getStatusCode());

        System.out.println(result.getServerResponse());
        Contact[] contacts = new Gson().fromJson(result.getServerResponse(), Contact[].class);
        for(int i=0; i < contacts.length; i++) {
            Contact c = contacts[i];
            _map.put(c.contID, c);
        }
    }

    private static void getOneFromSever() {

    }
}

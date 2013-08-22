import java.io.Console;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


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
            loadAllFromServer();
        }


    }

    public static void forceContactLoad(){
        loadAllFromServer();
    }

    private static void loadAllFromServer() {
        _timeStamp = new Date();
        RESTResult result = get("http://shear-n-dipity.com/api/contacts");

        _map = new HashMap<Integer, Contact>();

        System.out.println(result.getStatusCode());

        System.out.println(result.getServerResponse());
        Contact[] contacts = new Gson().fromJson(result.getServerResponse(), Contact[].class);

    }



}

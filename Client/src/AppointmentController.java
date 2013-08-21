/*

import sun.net.www.http.HttpClient;

import javax.json.JsonObject;
import java.io.IOException;

*/
/**
 * Created with IntelliJ IDEA.
 * User: mindikingsun
 * Date: 15/08/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 *//*

//<<<<<<< HEAD
public class AppointmentController implements RestAPI {

    */
/**
     * Can be used to return an appointment for confirmation, editing or cancellation.
     *//*

    @Override
    public void get() {
        try {
            HttpClient request = new HttpClient("http://shear-n-dipity.com/api/appointment/");



        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void post() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    */
/**
     * Can be used to return all appointments.
     *//*



    */
/**
     * Sends Json data to the server so the database can create the appointment.
     * Does not contain an ID.
     *//*

    @Override
    public void post(Appointment appointment) {
        String jsonAppointment = appointment.toJson();
        //HttpClient httpClient = new DefaultHttpClient();
        HttpClient httpclient = new HttpClient()
        try {
            HttpPost request = new HttpPost("http://shear-n-dipity.com/api/appointment/");
        }

    }

    */
/**
     * Sends Json data to server, with ID so that a particular appointment can be cancelled.
     *//*

    @Override
    public void delete() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    */
/**
     * Sends Json data to server, with ID so that a particular appointment can be updated.
     *//*

    @Override
    public void put() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
=======
public class AppointmentController {
>>>>>>> 6c6e9db55bad93fb5e49d3a29f17d45a8752fc8a
}
*/

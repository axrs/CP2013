package Models.Commands;

import Models.RESTCommand;
import Models.Config;

import java.net.HttpURLConnection;

public class GetContactsCommand extends RESTCommand {

    @Override
    public String getTarget() {
        return Config.getInstance().getServer() + "/api/contacts";
    }

    @Override
    public String getMethod() {
        return "GET";
    }

    @Override
    public void writeData(HttpURLConnection connection) {
        //Do nothing for Get.
    }
}

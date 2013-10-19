package Models;

import Interfaces.Command;
import Utilities.LogEventDispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RESTCommand implements Command {

    private RESTResponse response = new RESTResponse();

    private static String ReadStream(InputStream stream) throws IOException {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[4096];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            result = buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
        return result;
    }

    public RESTResponse getResponse() {
        return response;
    }

    public abstract String getTarget();

    public abstract String getMethod();

    public abstract void writeData(HttpURLConnection connection);

    @Override
    public void execute() {

        try {

            URL url = new URL(getTarget());
            HttpURLConnection _connection = (HttpURLConnection) url.openConnection();

            _connection.setDoOutput(true);
            _connection.setInstanceFollowRedirects(true);
            _connection.setRequestMethod(getMethod());
            _connection.setRequestProperty("Content-Type", "application/json");  //must be json
            _connection.setRequestProperty("Accept", "application/json");         //must be json

            writeData(_connection);
            LogEventDispatcher.log("Connected...?");

            response = new RESTResponse(_connection.getResponseCode(), ReadStream(_connection.getInputStream()));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

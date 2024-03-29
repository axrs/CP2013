package dao.restDAO.client;

import utilities.LogEventDispatcher;
import dao.restDAO.events.Result;
import dao.restDAO.requests.Request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveRESTClient {

    private static ActiveRESTClient instance = null;
    private BlockingQueue<Request> recordQueue = new LinkedBlockingQueue<Request>();
    private boolean isRunning = true;

    protected ActiveRESTClient() {
        new Thread(requester()).start();
    }

    public static void addRequest(Request request) {
        getInstance().add(request);
    }

    public static ActiveRESTClient getInstance() {
        if (instance == null) {
            instance = new ActiveRESTClient();
        }
        return instance;
    }

    private Runnable requester() {
        return new Runnable() {
            private String ReadStream(InputStream stream) throws IOException {
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

            private void makeRequest(Request request) {
                Result result = new Result(this);
                HttpURLConnection connection = null;
                try {
                    if (!request.getTarget().isEmpty()) {
                        String location = Request.getLocation() + request.getTarget();
                        location += location.endsWith("/") ? "" : "/";
                        location += "?access_token=" + Request.getToken();
                        URL url = new URL(location);
                        connection = (HttpURLConnection) url.openConnection();

                        LogEventDispatcher.log("Attempting URL Connection to: " + location);

                        connection.setDoOutput(true);
                        connection.setInstanceFollowRedirects(true);
                        connection.setRequestMethod(request.getMethod());

                        connection.setRequestProperty("Content-Type", "application/json");  //must be json
                        connection.setRequestProperty("Accept", "application/json");         //must be json

                        if (!request.getMethod().equals("GET") && !request.getMessage().equals("DELETE")) {
                            LogEventDispatcher.log("Writing JSON Data.");
                            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                            writer.write(request.getMessage());
                            writer.flush();
                            writer.close();
                        }

                        String response = ReadStream(connection.getInputStream());
                        LogEventDispatcher.log(connection.getResponseCode() + "\t" + response);
                        result = new Result(this, connection.getResponseCode(), response);
                        connection.disconnect();
                    }

                } catch (Exception ex) {
                    LogEventDispatcher.log("REST Exception Occurred: " + ex.toString());
                    if (connection != null) {
                        try {
                            result = new Result(this, connection.getResponseCode(), "");
                        } catch (IOException e) {
                            LogEventDispatcher.log(e.toString());
                        }
                    }

                } finally {
                    LogEventDispatcher.log("Dispatching Result.");
                    request.resultReturned(result);
                }
            }

            @Override
            public void run() {
                while (isRunning) {
                    if (recordQueue.size() > 0) {
                        try {
                            Request request = recordQueue.take();
                            makeRequest(request);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    public void add(Request request) {
        try {
            recordQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


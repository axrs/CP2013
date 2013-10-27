package client.controllers;

import dao.DAO;
import javafx.stage.Stage;

public class LoginSuccessCommand implements ICommand {

    private Stage stage = null;
    private String token = "";

    public LoginSuccessCommand(Stage stage) {
        this.stage = stage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void execute() {
        DAO.getInstance().getUserDAO().setUser(token);

        if (stage != null) {
            this.stage.close();
        }
    }
}

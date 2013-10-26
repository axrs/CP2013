package client.controllers;

import javafx.stage.Stage;

public class CloseStageCommand implements ICommand {

    private Stage stage;

    public CloseStageCommand(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void execute() {
        stage.close();
    }
}

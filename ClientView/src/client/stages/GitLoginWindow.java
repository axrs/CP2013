package client.stages;

import Models.Config;
import client.controllers.ICommand;
import client.controllers.LoginSuccessCommand;
import client.controllers.ShowLoginCommand;
import client.controllers.adapters.WindowEventStrategy;
import client.scene.CoreScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GitLoginWindow extends Stage implements ICommand {
    private LoginSuccessCommand onSuccess = new LoginSuccessCommand(this);
    private ICommand onFailure = null;

    public GitLoginWindow() {
    }


    public GitLoginWindow(LoginSuccessCommand onSuccess) {
        this.onSuccess = onSuccess;
        this.setOnCloseRequest(new WindowEventStrategy(new ShowLoginCommand()));
    }


    public GitLoginWindow(LoginSuccessCommand onSuccess, ICommand onFailure) {
        this.onSuccess = onSuccess;
        this.setOnCloseRequest(new WindowEventStrategy(onFailure));
    }

    @Override
    public void execute() {

        WebView webView = new WebView();

        final WebEngine webEngine = webView.getEngine();
        webEngine.load(Config.getInstance().getGithubURL());

        setScene(new CoreScene(webView, 1020, 590));

        this.toFront();

        webEngine.getLoadWorker().stateProperty().addListener(onTitleChange(webEngine));
        this.show();
    }

    private ChangeListener<Worker.State> onTitleChange(final WebEngine webEngine) {
        return new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State state2) {
                if (webEngine.getLocation().endsWith("/api/token")) {
                    onSuccess.setToken(webEngine.getDocument().getElementsByTagName("title").item(0).getTextContent());
                    onSuccess.execute();
                }
            }
        };
    }
}

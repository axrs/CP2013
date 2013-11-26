package client.stages;

import Models.Config;
import client.scene.CoreScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class GitLoginWindow extends Stage{

    public GitLoginWindow(){
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(Config.getInstance().getGithubURL());

        setScene(new CoreScene(webView, 1020, 590));
    }
}

package client.stages;

import client.controllers.CloseStageCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 26/11/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatWindow extends Stage {

    final static private ObservableList<String> graphChoices = FXCollections.observableArrayList();


    public StatWindow() {

        setTitle("CP2013 Appointment Scheduler - Stats");
        BorderPane borderPane = new BorderPane();

        Label heading = LabelFactory.createHeadingLabel("Statistics");

        final Node[] graph = new Node[1];
        graphChoices.addAll("Graph 1", "Graph 2", "Graph 3");
        final ComboBox<String> graphs = new ComboBox<String>(graphChoices);
        graphs.setValue("Graph 1");
        graphs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedGraph = graphs.getValue();
                if (selectedGraph.equals("Graph 1")){
                    graph[0] = makeBarChart();
                }
                else if (selectedGraph.equals("Graph 2")){
                    graph[0] = makeLineChart();
                }
                else if (selectedGraph.equals("Graph 3")){
                    graph[0] = makePieChart();
                }
            }
        });




        ActionButtons buttons = new ActionButtons(false);
        buttons.setOnCloseAction(new ActionEventStrategy(new CloseStageCommand(this)));

        HBox topPane = new HBox();
        topPane.getChildren().addAll(heading, graphs);
        topPane.setAlignment(Pos.CENTER);
        topPane.getStyleClass().addAll("grid");

        borderPane.setTop(topPane);
        borderPane.setCenter(graph[0]);
        borderPane.setBottom(buttons);

        setScene(new CoreScene(borderPane));
    }

    private Node makePieChart() {
        PieChart pieChart = new PieChart();
        return pieChart;
    }

    private Node makeLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        //TODO implement adding of data here
        lineChart.getData().addAll(series);
        return lineChart;
    }

    private Node makeBarChart() {

        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);
        barChart.setTitle("Things and Stuff");
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Hairdresser");

        XYChart.Series series = new XYChart.Series();
        //TODO implement adding of data here
        barChart.getData().addAll(series);
        return barChart;
    }

    }


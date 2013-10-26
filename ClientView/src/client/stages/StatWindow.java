package client.stages;

import client.controllers.windows.core.CloseStageCommand;
import client.controllers.adapters.ActionEventStrategy;
import client.scene.CoreScene;
import client.scene.control.ActionButtons;
import client.scene.control.LabelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
    final BorderPane borderPane = new BorderPane();


    public StatWindow() {

        setTitle("CP2013 Appointment Scheduler - Stats");


        Label heading = LabelFactory.createHeadingLabel("Statistics");

        graphChoices.addAll("Graph 1", "Graph 2", "Graph 3");
        final ComboBox<String> graphs = new ComboBox<String>(graphChoices);

        graphs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedGraph = graphs.getValue();
                if (selectedGraph.equals("Graph 1")) {
                    makeBarChart();
                } else if (selectedGraph.equals("Graph 2")) {
                    makeLineChart();
                } else if (selectedGraph.equals("Graph 3")) {
                    makePieChart();
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
        borderPane.setBottom(buttons);
        makeBarChart();
    }

    private void makePieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Joey", 23),
                new PieChart.Data("Felicity", 77));
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Hairdressers");

        borderPane.setCenter(pieChart);
        setScene(new CoreScene(borderPane));
    }

    private void makeLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        //TODO implement adding of data here
        lineChart.getData().addAll(series);
        borderPane.setCenter(lineChart);
        setScene(new CoreScene(borderPane));
    }

    private void makeBarChart() {

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
        borderPane.setCenter(barChart);
        setScene(new CoreScene(borderPane));
    }

}


package client.controllers;

import Models.ServiceProvider;
import client.scene.control.Agenda;
import dao.DAO;
import javafx.application.Platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class ReloadAgendaProvidersCommand implements ICommand {

    private Agenda agendaView = null;

    public ReloadAgendaProvidersCommand(Agenda agendaView) {
        this.agendaView = agendaView;
    }

    @Override
    public void execute() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                agendaView.appointmentGroups().clear();
                ArrayList<Agenda.AppointmentGroup> addList = new ArrayList<Agenda.AppointmentGroup>();
                ArrayList<String> styles = new ArrayList<String>();
                ServiceProvider[] providers = DAO.getInstance().getProviderDAO().getStore();

                int i = 0;
                for (ServiceProvider p : providers) {
                    Agenda.AppointmentGroup grp = new Agenda.AppointmentGroupImpl().withStyleClass("group" + String.valueOf(i));
                    grp.setDescription(p.getName() + " " + p.getSurname());

                    styles.add(
                            String.format(".%s {-fx-background-color: %s; } ",
                                    grp.getStyleClass(),
                                    p.getColor())
                    );

                    addList.add(grp);
                    i++;
                }

                try {
                    File file = new File("./src/styles/agenda.css");
                    file.getParentFile().mkdirs();

                    PrintWriter writer = new PrintWriter(file, "UTF-8");
                    for (String s : styles) {
                        writer.println(s);
                    }
                    writer.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                agendaView.appointmentGroups().addAll(addList);
                agendaView.getStylesheets().add("./styles/agenda.css");

            }
        });
    }
}

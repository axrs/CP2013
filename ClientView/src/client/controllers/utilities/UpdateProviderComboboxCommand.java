package client.controllers.utilities;

import client.controllers.ICommand;
import dao.DAO;
import javafx.scene.control.ComboBox;
import models.ServiceProvider;

public class UpdateProviderComboboxCommand implements ICommand {

    private ComboBox comboBox = null;

    public UpdateProviderComboboxCommand(ComboBox comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public void execute() {
        ServiceProvider[] providers = DAO.getInstance().getProviderDAO().getStore();
        comboBox.getItems().clear();
        comboBox.getItems().add("All Hairdressers");
        for(ServiceProvider p:providers) {
            comboBox.getItems().add(p.getName());
        }
    }
}

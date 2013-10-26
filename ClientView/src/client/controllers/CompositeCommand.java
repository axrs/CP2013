package client.controllers;

import java.util.ArrayList;

public class CompositeCommand implements ICommand {

    private ArrayList<ICommand> commands = new ArrayList<ICommand>();

    public void addCommand(ICommand c) {
        commands.add(c);
    }

    public void removeCommand(ICommand c) {
        this.commands.remove(c);
    }

    @Override
    public void execute() {
        for (ICommand c : commands) {
            c.execute();
        }
    }
}

package Models;

import Interfaces.IRESTCallback;

public class CommandCallbackWrapper {
    private IRESTCallback _event;
    private RESTCommand _command;

    public CommandCallbackWrapper(RESTCommand command, IRESTCallback event) {
        this._event = event;
        this._command = command;
    }

    public IRESTCallback getEvent() {
        return _event;
    }

    public RESTCommand getCommand() {
        return _command;
    }
}

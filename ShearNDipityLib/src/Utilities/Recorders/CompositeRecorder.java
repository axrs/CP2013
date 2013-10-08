package Utilities.Recorders;

import java.util.ArrayList;
import java.util.List;

public class CompositeRecorder implements IRecorder {

    private List<IRecorder> _recorders;

    protected CompositeRecorder() {
        _recorders = new ArrayList<IRecorder>();
    }

    @Override
    public void record(String message) {
        for (IRecorder l : _recorders) {
            l.record(message);
        }
    }

    public void add(IRecorder recorder) {
        _recorders.add(recorder);
    }

    public void remove(IRecorder recorder) {
        if (_recorders.contains(recorder)) {
            _recorders.remove(recorder);
        }
    }
}

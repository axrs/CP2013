package utilities.recorders;

public class SingletonCompositeRecorder extends CompositeRecorder {

    private static SingletonCompositeRecorder _instance = null;

    protected SingletonCompositeRecorder() {
        super();
    }

    public static SingletonCompositeRecorder getInstance() {
        if (_instance == null) {
            _instance = new SingletonCompositeRecorder();
        }
        return _instance;
    }
}

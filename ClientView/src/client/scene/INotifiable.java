package client.scene;

public interface INotifiable {
    public void onInformation(final String message);

    public void onValidationError(final String message);

    public void onError(final String message);

    public void onSuccess();
}

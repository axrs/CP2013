package Models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamLogger extends AbstractLogger {

    FileOutputStream _stream = null;
    private String _filePath = "";

    public FileStreamLogger(String filePath) {
        _filePath = filePath;
    }

    public void prepare() {
        if (_stream == null && !_filePath.isEmpty()) {
            try {
                _stream = new FileOutputStream(_filePath, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeMessage() {
        writeStream(getMessage().toString());
    }

    private void writeStream(String message) {
        if (_stream != null) {
            try {
                _stream.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void finalise() {
        writeStream("\n");
    }
}

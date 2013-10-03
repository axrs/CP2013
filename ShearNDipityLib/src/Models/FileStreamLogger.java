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

    public void prepare() throws FileNotFoundException {
        if (_stream == null && !_filePath.isEmpty()) {
            _stream = new FileOutputStream(_filePath, true);
        }
    }

    public void writeMessage() throws IOException {
        writeStream(getMessage().toString());
    }

    private void writeStream(String message) throws IOException {
        if (_stream != null) {
            _stream.write(message.getBytes());
        }
    }

    public void finalise() {
        try {
            writeStream("\n");
        } catch (IOException e) {
        }
    }
}

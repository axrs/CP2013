package Utilities.Recorders;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamRecorder implements IRecorder {

    private FileOutputStream _stream = null;

    public FileStreamRecorder(String filePath) {

        if (_stream == null && !filePath.isEmpty()) {
            try {
                _stream = new FileOutputStream(filePath, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void record(String message) {
        if (_stream != null) {
            try {
                _stream.write(message.getBytes());
                _stream.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

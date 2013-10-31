package utilities.recorders;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatedFileStreamRecorder implements IRecorder {

    String folderPath = "logs";
    String fileName = "";
    FileStreamRecorder recorder = null;

    public DatedFileStreamRecorder(String folderPath) {
        if (!folderPath.isEmpty()) {
            this.folderPath = folderPath;
        }
        File dir = new File(folderPath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        fileName = String.format("%s.log", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        recorder = new FileStreamRecorder(String.format("%s%s%s", folderPath, File.separator, fileName));
    }

    @Override
    public void record(String message) {
        if (recorder != null) {
            recorder.record(message);
        }
    }
}

package Utilities.Loggers;

import java.text.DateFormat;
import java.util.Date;

public class LocaleFormatStrategy implements IFormatStrategy {

    public String format(String message) {
        return String.format("%s\t%s", formatDate(), message);
    }

    String formatDate() {
        return DateFormat.getDateInstance().format(new Date());
    }
}

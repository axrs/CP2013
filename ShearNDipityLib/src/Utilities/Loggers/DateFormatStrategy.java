package Utilities.Loggers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatStrategy extends LocaleFormatStrategy {

    @Override
    String formatDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}

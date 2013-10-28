package utilities.loggers.strategies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatStrategy extends LocaleFormatStrategy {
    @Override
    String formatDate() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
}

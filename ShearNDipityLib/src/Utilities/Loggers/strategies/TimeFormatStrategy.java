package utilities.loggers.strategies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatStrategy extends LocaleFormatStrategy {

    @Override
    String formatDate() {
        return new SimpleDateFormat("hh:mm:ss").format(new Date());
    }
}

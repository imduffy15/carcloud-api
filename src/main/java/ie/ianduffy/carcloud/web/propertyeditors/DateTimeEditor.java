package ie.ianduffy.carcloud.web.propertyeditors;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

public class DateTimeEditor extends PropertyEditorSupport {

    private final boolean allowEmpty;
    private final DateTimeFormatter formatter;

    public DateTimeEditor(String dateFormat, boolean allowEmpty) {
        this.formatter = DateTimeFormat.forPattern(dateFormat);
        this.allowEmpty = allowEmpty;
    }

    public String getAsText() {
        Date value = (Date) getValue();
        return value != null ? new DateTime(value).toString(formatter) : "";
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(new DateTime(formatter.parseDateTime(text)));
        }
    }
}

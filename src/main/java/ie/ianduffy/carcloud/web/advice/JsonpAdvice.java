package ie.ianduffy.carcloud.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    JsonpAdvice() {
        super("callback");
    }

}

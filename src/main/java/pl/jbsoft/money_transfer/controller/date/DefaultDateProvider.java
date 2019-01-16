package pl.jbsoft.money_transfer.controller.date;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
@Primary
public class DefaultDateProvider implements DateProvider {

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}

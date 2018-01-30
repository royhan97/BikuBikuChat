package com.example.adhit.bikubiku.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Created by roy on 1/10/2018.
 */

public class DateFormatterPresenter {

    public String dateFormatter (String stringDate){
        String result = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter timeFormatter;
            timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            TemporalAccessor accessor = timeFormatter.parse(stringDate);
            Date date = Date.from(Instant.from(accessor));
            result = new SimpleDateFormat("dd/MM/yyyy").format(date);
        }
        else {
            String[] parts = stringDate.split("T");
            String part1 = parts[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // use SimpleDateFormat to define how to PARSE the INPUT
            try {
                Date datef = sdf.parse(part1);
                result = new SimpleDateFormat("dd/MM/yyyy").format(datef);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}

package capstone_back.utils.timeStamp;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CurrentTime {
    public Timestamp currentTimeToTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = dateFormat.format(timestamp);
        return Timestamp.valueOf(format);
    }
}

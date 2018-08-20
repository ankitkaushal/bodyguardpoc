package healthapp.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "entries")
public class Entry {
    @Ignore
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "type")
    private String type;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    private void setTimestampFromDate() {
        this.timestamp = simpleDateFormat.format(date);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @ColumnInfo(name ="timestamp")
    private String timestamp;

    @Ignore
    private Date date;

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public Date getDate()  {
        try {
            return simpleDateFormat.parse(timestamp);
        } catch (ParseException e) {
            return  null;
        }
    }

    public void setDate(Date date) {
        this.date = date;
        setTimestampFromDate();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "simpleDateFormat=" + simpleDateFormat +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", date=" + date +
                '}';
    }

    public String format(){
        return type + " " + timestamp.split("T")[1];
    }
}

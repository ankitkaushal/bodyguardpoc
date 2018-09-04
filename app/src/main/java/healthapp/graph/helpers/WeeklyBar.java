package healthapp.graph.helpers;

import android.os.AsyncTask;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import healthapp.dbhelpers.EntryDao;
import healthapp.dbhelpers.EntryTypeDao;
import healthapp.entities.Entry;
import healthapp.entities.EntryType;
import healthapp.utils.DateUtil;

/**
 * Created by ankit.kaushal on 8/17/2018.
 */

public class WeeklyBar implements BarEntriesProvider {

    String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    int weeklyScore = 0;





    @Override
    public String getFormattedValue(float value, AxisBase axis) {


        return  days[(int)value-1];


    }

    @Override
    public List<BarEntry> getBarEntries(final EntryDao entryDao ,final EntryTypeDao entryTypeDao)  {
               weeklyScore = 0;

                List<BarEntry> barEntries = new ArrayList<>();

                List<EntryType> types = entryTypeDao.getAll();

                Map<String,EntryType> typeMap = makeEntryTypeMap(types);

                Map<Integer,Integer> scoreMap = new HashMap<>();
                List<Entry> entries = entryDao.getAll();
                for(Entry entry:entries){
                    Date today = new Date();

                   int entryDayOfWeek = getDayOfWeek(entry.getDate());
                   int weekOfYear = getWeekOfYear(today);
                   int entryWeekOfYear = getWeekOfYear(entry.getDate());

                    EntryType entryType = typeMap.get(entry.getType());

                   if(weekOfYear == entryWeekOfYear && entry.getDate().getYear() == today.getYear()){
                       int score = entryType.getScore();
                       int dayEntry = scoreMap.get(entryDayOfWeek)==null? 0: scoreMap.get(entryDayOfWeek);
                       scoreMap.put(entryDayOfWeek,dayEntry+score);
                   }


                }


            for(int i=1;i<8;i++){
                Integer score = scoreMap.get(i)==null?0:scoreMap.get(i);
                weeklyScore+=score;
                barEntries.add(new BarEntry(i,score));
            }

            return barEntries;


    }

    @Override
    public String getInformation() {
        return "Weekly score: " + weeklyScore;
    }

    private Map<String,EntryType> makeEntryTypeMap(List<EntryType> types) {

        Map<String,EntryType> typeMap = new HashMap<>();
        for(EntryType entryType:types){
            typeMap.put(entryType.getName(),entryType);
        }
        return typeMap;
    }



    private int getDayOfWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    private int getWeekOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        return weekOfYear;
    }
}

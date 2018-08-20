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


    public int getWeeklyScore() {
        return weeklyScore;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;

        int valueOfX = dayOfWeek-(int)(7-value);
        if(valueOfX<0){
            valueOfX+=7;
        }

        return  days[valueOfX];


    }

    @Override
    public List<BarEntry> getBarEntries(final EntryDao entryDao ,final EntryTypeDao entryTypeDao)  {
                List<BarEntry> barEntries = new ArrayList<>();

                List<EntryType> types = entryTypeDao.getAll();

                Map<String,EntryType> typeMap = makeEntryTypeMap(types);

                Map<Integer,Integer> scoreMap = new HashMap<>();
                List<Entry> entries = entryDao.getAll();
                for(Entry entry:entries){
                    int differenceInDays = getDateDiff(entry.getDate(), DateUtil.tommorowFirstInstant());
                    EntryType entryType = typeMap.get(entry.getType());
                    int score = entryType.getScore();

                    int dayEntry = scoreMap.get(differenceInDays)==null? 0: scoreMap.get(differenceInDays);
                    scoreMap.put(differenceInDays,dayEntry+score);
                    System.out.println(scoreMap);
                }


            for(int i=0;i<7;i++){
                Integer score = scoreMap.get(i)==null?0:scoreMap.get(i);
                weeklyScore+=score;
                barEntries.add(new BarEntry(7-i,score));
            }

            return barEntries;


    }

    private Map<String,EntryType> makeEntryTypeMap(List<EntryType> types) {

        Map<String,EntryType> typeMap = new HashMap<>();
        for(EntryType entryType:types){
            typeMap.put(entryType.getName(),entryType);
        }
        return typeMap;
    }

    public static int getDateDiff(Date date1, Date date2) {
        Days days = Days.daysBetween(new DateTime(date1.getTime()),new DateTime(date2.getTime()));
        return  days.getDays();
    }
}

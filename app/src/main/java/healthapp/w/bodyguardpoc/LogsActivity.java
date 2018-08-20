package healthapp.w.bodyguardpoc;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import healthapp.dbhelpers.AppDatabase;
import healthapp.dbhelpers.EntryDao;
import healthapp.entities.Entry;
import healthapp.listeners.SwipeHelper;
import healthapp.utils.DateUtil;

public class LogsActivity extends AppCompatActivity {

    SwipeHelper helper = new SwipeHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        final LinearLayout layout = findViewById(R.id.logs_layout);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final EntryDao entryDao = db.dao();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Entry> entries =  entryDao.getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            insertTextInLayout(layout,entries);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    private void insertTextInLayout(LinearLayout layout,List<Entry> entries) throws ParseException {
//        Collections.sort(entries, new Comparator<Entry>() {
//            @Override
//            public int compare(Entry o1, Entry o2) {
//                return 0;
//            }
//        });

        Map<String, List<Entry>> entriesMap = new TreeMap<>();


        for(Entry entry:entries){
            String date = DateUtil.getDateAsString(entry.getDate());
            List<Entry> dateEntries = entriesMap.get(date);
            if(dateEntries == null){
                dateEntries = new ArrayList<>();
                dateEntries.add(entry);
            }else{
                dateEntries.add(entry);
            }
            entriesMap.put(date,dateEntries);


        }

     //   NavigableMap<Date,List<Entry>> navigableMap = ((TreeMap<Date,List<Entry>>)entriesMap).descendingMap();

        for(String date:((TreeMap<String,List<Entry>>)entriesMap).descendingKeySet()){
            TextView tv = new TextView(LogsActivity.this);
            tv.setText(date.toString());
            layout.addView(tv);
            tv.setBackgroundColor(Color.BLUE);

            List<Entry> entryList = entriesMap.get(date);

            for(Entry entry:entryList){
                TextView entryTv = new TextView(LogsActivity.this);
                entryTv.setText(entry.format());
                layout.addView(entryTv);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return helper.onTouch(event,LogsActivity.this,MainActivity.class,StatisticsActivity.class);
    }
}

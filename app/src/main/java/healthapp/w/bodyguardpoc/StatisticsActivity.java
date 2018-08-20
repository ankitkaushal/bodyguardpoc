package healthapp.w.bodyguardpoc;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import healthapp.dbhelpers.AppDatabase;
import healthapp.dbhelpers.EntryDao;
import healthapp.dbhelpers.EntryTypeDao;
import healthapp.graph.helpers.BarEntriesProvider;
import healthapp.graph.helpers.WeeklyBar;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        plotChart();

    }


    private void plotChart(){

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final EntryTypeDao dao = db.typeDao();
        final EntryDao entryDao = db.dao();

       final BarChart chart1 = (BarChart) findViewById(R.id.chart1);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final  BarEntriesProvider provider = new WeeklyBar();
                List<BarEntry> entries = provider.getBarEntries(entryDao,dao);
                BarDataSet bardataset = new BarDataSet(entries, "Score");

                final BarData data = new BarData(bardataset);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chart1.setData(data);
                        chart1.getXAxis().setValueFormatter(provider);

                        Description description = new Description();
                        description.setText(provider.getInformation());
                        description.setTextSize(12f);
                        chart1.setDescription(description);
                        chart1.animateXY(2000, 2000);
                        chart1.invalidate();

                    }
                });
            }
        });
    }
}

package healthapp.w.bodyguardpoc;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Mon");
        xAxis.add("Tue");
        xAxis.add("Wed");
        xAxis.add("Thurs");
        xAxis.add("Fri");
        xAxis.add("Sat");
        xAxis.add("Sun");
        return xAxis;
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(80.000f, 6); // Jun
        valueSet1.add(v1e7);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, " ");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
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
                BarDataSet bardataset = new BarDataSet(entries, "Cells");

                final BarData data = new BarData(bardataset);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chart1.setData(data);
                        chart1.getXAxis().setValueFormatter(provider);
                        //chart1.setDescription(new Description("desc"));
                        chart1.animateXY(2000, 2000);
                        chart1.invalidate();
                    }
                });
            }
        });
    }
}

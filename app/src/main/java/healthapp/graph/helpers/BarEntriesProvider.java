package healthapp.graph.helpers;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParseException;
import java.util.List;

import healthapp.dbhelpers.EntryDao;
import healthapp.dbhelpers.EntryTypeDao;

/**
 * Created by ankit.kaushal on 8/17/2018.
 */

public interface BarEntriesProvider extends IAxisValueFormatter {
    List<BarEntry> getBarEntries(EntryDao entryDao, EntryTypeDao entryTypeDao) ;
    String getInformation();
}

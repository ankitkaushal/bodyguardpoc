package healthapp.dbhelpers;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import healthapp.entities.Entry;
import healthapp.entities.EntryType;
import healthapp.entities.EntryTypeBuilder;

public class DBUtil {

    public static int latestEntryId;
    public static void initializeTypes(EntryTypeDao dao){
        dao.deleteAll();

        EntryType fruit = EntryTypeBuilder.anEntryType().withName("fruit").withScore(1).withActive(true).withImage("fruit").build();

        dao.insert(fruit);

        EntryType greenTea = EntryTypeBuilder.anEntryType().withName("greentea").withScore(1).withActive(true).withImage("greentea").build();

        dao.insert(greenTea);
    }

   public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
// Since we didn't alter the table, there's nothing else to do here.
            //database.execSQL("drop table entry_types");
        }
    };

    public static void getLatestEntryId(EntryDao dao){
        List<Entry> entries = dao.getAll();
      Collections.sort(entries,new Comparator<Entry>(){

          @Override
          public int compare(Entry o1, Entry o2) {
              return o2.getId()-o1.getId();
          }
      });

      latestEntryId = entries.get(0).getId();
    }
}

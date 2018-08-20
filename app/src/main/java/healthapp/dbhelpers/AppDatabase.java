package healthapp.dbhelpers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import healthapp.entities.Entry;
import healthapp.entities.EntryType;

@Database(entities = {Entry.class, EntryType.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntryDao dao();
    public abstract EntryTypeDao typeDao();
}

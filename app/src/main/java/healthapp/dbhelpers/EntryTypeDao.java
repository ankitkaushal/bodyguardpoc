package healthapp.dbhelpers;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import healthapp.entities.Entry;
import healthapp.entities.EntryType;

@Dao
public interface EntryTypeDao {

    @Insert
    void insert(EntryType entryType);

    @Query("SELECT * FROM entry_types")
    List<EntryType> getAll();

    @Query("DELETE from entry_types")
    void deleteAll();

}

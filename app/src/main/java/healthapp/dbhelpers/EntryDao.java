package healthapp.dbhelpers;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import healthapp.entities.Entry;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Query("SELECT * FROM entries")
    List<Entry> getAll();




}

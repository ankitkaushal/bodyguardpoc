package healthapp.listeners;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.view.View;

import java.util.Date;

import healthapp.dbhelpers.AppDatabase;
import healthapp.dbhelpers.DBUtil;
import healthapp.dbhelpers.EntryDao;
import healthapp.entities.Entry;
import healthapp.entities.EntryType;

/**
 * Created by ankit.kaushal on 8/12/2018.
 */

public class SaveEntryListener implements View.OnClickListener{


    EntryDao entryDao ;
    EntryType entryType;

    public SaveEntryListener(EntryDao entryDao, EntryType entryType) {
        this.entryDao = entryDao;
        this.entryType = entryType;
    }

    @Override
    public void onClick(View v) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                Entry entry = new Entry();
                int latestId = ++DBUtil.latestEntryId;

                entry.setId(latestId);
                entry.setDate(new Date());
                entry.setType(entryType.getName());
                entryDao.insert(entry);

                System.out.println(entryDao.getAll());
            }
        });

    }
}

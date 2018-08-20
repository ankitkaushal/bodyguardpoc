package healthapp.w.bodyguardpoc;



import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import healthapp.dbhelpers.AppDatabase;
import healthapp.dbhelpers.DBUtil;
import healthapp.dbhelpers.EntryDao;
import healthapp.dbhelpers.EntryTypeDao;
import healthapp.entities.Entry;
import healthapp.entities.EntryType;
import healthapp.listeners.SaveEntryListener;
import healthapp.listeners.SwipeHelper;

public class MainActivity extends AppCompatActivity {


    SwipeHelper helper = new SwipeHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridLayout gridLayout = findViewById(R.id.grid_layout);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        final EntryTypeDao dao = db.typeDao();
        final EntryDao entryDao = db.dao();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // dao.insert(entry);
                DBUtil.initializeTypes(dao);
                DBUtil.getLatestEntryId(entryDao);

                List<EntryType>entries= dao.getAll();
               for(final EntryType type:entries){
                   System.out.println(type.getName());

                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Button button = createButtonFromType(type,entryDao);

                           gridLayout.addView(button);
                       }
                   });

               }
            }
        });




    }

    private Button createButtonFromType(EntryType t, EntryDao entryDao){

        Button button = new Button(this);
        button.setBackgroundColor(0x5FAA1A);

        String image =t.getImage();
        String name = t.getName();

        int resid =getResources().getIdentifier(image,"drawable",getPackageName());
        System.out.println(resid+getPackageName());

        Drawable dr=getResources().getDrawable(resid);

        button.setCompoundDrawablesWithIntrinsicBounds(null, dr , null, null);
        button.setText(name);

        button.setOnClickListener(new SaveEntryListener(entryDao,t));

        return button;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

       return helper.onTouch(event,MainActivity.this,null,LogsActivity.class);


    }

}

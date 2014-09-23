package com.untilted.jezcardia.thirtymin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ActionList extends Activity {

    ListView actionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        // Create ListView
        actionList = (ListView) findViewById(R.id.listView);
        // Populate ListView
        DBHelper db = new DBHelper(this);
        List<Action> dbActions = db.getAllActions();

        // Kinda confused about Lists vs ArrayLists in this context actually
        ArrayList<Action> actions = new ArrayList<Action>();
        for(int i=0; i<dbActions.size(); i++){
            actions.add(dbActions.get(i));
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

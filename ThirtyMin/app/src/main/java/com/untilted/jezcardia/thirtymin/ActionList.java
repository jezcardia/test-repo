package com.untilted.jezcardia.thirtymin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ActionList extends ListActivity {

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        // Populate ListView
        List<Action> dbActions = db.getAllActions();
        db.close();

        // Kinda confused about Lists vs ArrayLists in this context actually
        ArrayList<String> actionNames = new ArrayList<String>();
        for(int i=0; i<dbActions.size(); i++)
            actionNames.add(dbActions.get(i).getName());

        // If there are no actions, I want to display filler text
        if(actionNames.size()==0){
            String blank = "Nothing to do!";
            actionNames.add(blank);
        }

        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, actionNames));

    }

    public void onListItemClick(ListView parent, View v,int position, long id) {
        ArrayAdapter<String> adapter =  (ArrayAdapter<String>) getListAdapter();
        // Get name of action
        String name = adapter.getItem(position);


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
        if (id == R.id.action_new){
            // Collect name for new action
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("New Action");
            alert.setMessage("Spend 30 minutes on:");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Let's go", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();

                    String value = input.getText().toString();
                    db.addAction(new Action(value));
                    db.close();

                    adapter.add(value);
                    adapter.notifyDataSetChanged();

                }
            });

            alert.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();


        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.carlesgm.todoproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context = this;
    private ArrayList<Note> items = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private NotesAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordLayout);

        ListView notesList = (ListView) findViewById(R.id.notesList);
        adapter = new NotesAdapter(this, items);
        notesList.setAdapter(adapter);
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(context, AddNoteActivity.class);
                i.putExtra("item", items.get(position));
                i.putExtra("position", position);

                startActivityForResult(i, 2);

            }
        });

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTask(View view) {
//                .setAction("Action", null).show();
        Intent i = new Intent(context, AddNoteActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Note result = data.getParcelableExtra("item");
                    adapter.add(result);

                    Snackbar.make(coordinatorLayout, "Nota agregada", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;

            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    Note result = data.getParcelableExtra("item");
                    int position = data.getIntExtra("position", -1);

                    if (result == null) {
                        if (position != -1) {
                            items.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Note aux = adapter.getItem(position);
                        aux.setTitle(result.getTitle());
                        aux.setDescription(result.getDescription());
                    }

                }
        }
    }//onActivityResult

}

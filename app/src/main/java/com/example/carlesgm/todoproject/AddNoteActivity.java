package com.example.carlesgm.todoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edTitol;
    private EditText edDescripcio;
    private Note item;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edTitol = (EditText) findViewById(R.id.edTitol);
        edDescripcio = (EditText) findViewById(R.id.edDescriptio);

        Intent i = getIntent();
        item = i.getParcelableExtra("item");
        position = i.getIntExtra("position", -1);

        if (item != null) {
            edTitol.setText(item.getTitle());
            edDescripcio.setText(item.getDescription());
        }
    }

    public void onGuardarClick() {
        String titol = edTitol.getText().toString();
        String descripcio = edDescripcio.getText().toString();

        Note result = new Note();
        result.setTitle(titol);
        result.setDescription(descripcio);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("item", result);
        returnIntent.putExtra("position", position);
        setResult(RESULT_OK, returnIntent);

        finish();
    }

    public void onDeleteclick() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("position", position);
        setResult(RESULT_OK, returnIntent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (item == null)
            getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        else
            getMenuInflater().inflate(R.menu.menu_note_detail_delete, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miOK) {
            onGuardarClick();
            return true;
        }

        if (id == R.id.miCancel) {
            finish();
            return true;
        }

        if (id == R.id.miDelete) {
            onDeleteclick();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

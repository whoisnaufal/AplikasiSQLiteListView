package com.mozzastudio.aplikasisqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Map<String, Object>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                final int id = (Integer) arrayList.get(position).get("id");
                final String nama = (String) arrayList.get(position).get("nama");
                final String alamat = (String) arrayList.get(position).get("alamat");
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("nama", nama);
                intent.putExtra("alamat", alamat);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                final int id = (Integer) arrayList.get(position).get("id");
                tampilkanDialogKonfirmasiHapus(id);
                return true;
            }
        });
    }

    void tampilkanDialogKonfirmasiHapus(final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data ini?")
                .setMessage("Apakah Anda yakin ingin menghapus data in?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(id);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
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

    void getList() {
        DatabaseHelper db = new DatabaseHelper(this);
        arrayList = db.GetUsers();

        ListView lv = findViewById(R.id.listView);
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, arrayList, android.R.layout.simple_list_item_2, new String[]{"nama", "alamat"}, new int[]{android.R.id.text1, android.R.id.text2});
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void deleteUser(int id) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.delete(id);
        arrayList.clear();
        getList();
    }
}

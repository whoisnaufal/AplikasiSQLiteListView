package com.mozzastudio.aplikasisqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    EditText etNama, etAlamat;
    Button btnSimpan, btnCancel;
    DatabaseHelper databaseHelper;
    int id;
    String nama, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etNama = (EditText) findViewById(R.id.etNama);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        databaseHelper = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        id = -1;
        if (extras != null) {
            getSupportActionBar().setTitle("Ubah Data");
            id = extras.getInt("id");
            nama = extras.getString("nama");
            alamat = extras.getString("alamat");
            etNama.setText(nama);
            etAlamat.setText(alamat);
        } else {
            getSupportActionBar().setTitle("Tambah Data");
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String alamat = etAlamat.getText().toString();
                if (id == -1) {
                    databaseHelper.addStudentDetail(nama, alamat);
                } else {
                    databaseHelper.update(id, nama, alamat);
                }
                Toast.makeText(InputActivity.this, "Data tersimpan!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

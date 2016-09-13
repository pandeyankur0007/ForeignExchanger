package com.augylab.ankur.foreignexchanger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recordRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AddRecord> listArray = new ArrayList<>();
    final static int REQUEST_CODE = 101;
    final static int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recordRecyclerView = (RecyclerView) findViewById(R.id.record_recycler_view);

        assert recordRecyclerView != null;
        recordRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recordRecyclerView.setLayoutManager(mLayoutManager);



        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            listArray = dbHelper.getRecord();
            mAdapter = new CustomHomeAdapter(this, listArray);

            recordRecyclerView.setAdapter(mAdapter);

        } catch (Exception ex) {
            Toast.makeText(HomeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_refresh:
                break;

            case R.id.action_arrow:
                Intent intent = new Intent(this, AddRecordActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_CODE) {
                Log.d("outcome", "I am in");
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

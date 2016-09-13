package com.augylab.ankur.foreignexchanger;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public EditText codeEt, quantityEt, paidValueEt;
    public CheckBox debitCheckBox;
    public Button submitBtn;
    public SimpleDateFormat sdf;
    public String date;
    public AddRecord addRecord;
    public int qty;
    public double paidValue;
    public String history;
    public String code;
    private int RESULT_CODE=1;
    private Boolean debitStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("ADD CURRENCY");

        codeEt = (EditText) findViewById(R.id.codeEt);
        quantityEt = (EditText) findViewById(R.id.quantityEt);
        paidValueEt = (EditText) findViewById(R.id.paidValueEt);
        debitCheckBox = (CheckBox) findViewById(R.id.debitCheckBox);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(this);


        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        date = sdf.format(d);

        debitCheckBox.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        code = codeEt.getText().toString();
        qty = Integer.parseInt(quantityEt.getText().toString());
        paidValue = Double.parseDouble(paidValueEt.getText().toString());
        history = date + " " + qty + " " + paidValue + ",";

        DatabaseHelper db = new DatabaseHelper(this);
        boolean result = db.saveRecord(new AddRecord(code, qty, paidValue, date, history),debitStatus);
        Log.e("insert result : ", result+"");
        db.close();
        if (result == true) {

            Intent in = new Intent();
            in.putExtra("debitStatus", debitStatus);
            setResult(RESULT_CODE, in);
            finish();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //addRecord.debit(qty, paidValue);
            debitStatus = true;
        }
    }
}

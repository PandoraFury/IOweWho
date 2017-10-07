package com.monoceroses.iowewho;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class SingleTabActivity extends AppCompatActivity {

    //private ArrayList<Tab> tabList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private Tab currentTab;
    private View mView;
    private EditText mNewAmount;
    private TextView currentAmount;
    private Person person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the intent that started this activity, extract string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final FloatingActionButton mShowNewAmountDialog = (FloatingActionButton) findViewById(R.id.addNewAmount);

        //Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.tvTabName);
        currentAmount = (TextView) findViewById(R.id.tvCurrentAmount);
        textView.setText(message);
        currentTab = new Tab(message);

        //tabList.add(currentTab);

        final Button mNewNameGo = (Button) findViewById(R.id.btAddName);
        mNewNameGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText mAddName = (EditText) findViewById(R.id.etAddName);
                final String newName = mAddName.getText().toString();

                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                if (newName.isEmpty()) {
                    Toast.makeText(SingleTabActivity.this, "Enter a name!", Toast.LENGTH_LONG).show();
                } else {
                    if (Arrays.asList(nameList).contains(newName)){
                        Toast.makeText(SingleTabActivity.this,"Name already in list!",Toast.LENGTH_LONG).show();
                    }else {
                        nameList.add(newName);
                        person = new Person();
                        currentTab.addName(person);
                        mAddName.setText(null);
                        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(SingleTabActivity.this, android.R.layout.simple_list_item_1, nameList);
                        ListView listView = (ListView) findViewById(R.id.lvNames);
                        listView.setAdapter(mAdapter);
                    }
                }
            }
        });


        mShowNewAmountDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SingleTabActivity.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_new_amount, null);
                mNewAmount = (EditText) mView.findViewById(R.id.editTextAmount);
                Button mNewAmountGo = (Button) mView.findViewById(R.id.btAddNewAmount);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();

                mNewAmountGo.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Double newAmount;
                        String stNewAmount = mNewAmount.getText().toString();

                        if (stNewAmount.matches("[0-9]+") || stNewAmount.matches("[0-9]+\\.[0-9]{2}") || stNewAmount.matches("\\.[0-9]{2}")) {
                            newAmount = Double.parseDouble(mNewAmount.getText().toString());
                            dialog.dismiss();
                            addNewAmount(newAmount);

                        } else {
                            Toast.makeText(SingleTabActivity.this, "Please enter a number  like 999.99", Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }
        });
    }


    public void addNewAmount(double amount) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        currentTab.addToTotal(amount);
        //currentAmount.setText(Double.toString(currentTab.getCurrentTotal()));
        currentAmount.setText(defaultFormat.format(currentTab.getCurrentTotal()));
    }

}



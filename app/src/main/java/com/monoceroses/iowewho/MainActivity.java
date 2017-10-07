package com.monoceroses.iowewho;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE="com.monoceroses.iowewho.MESSAGE";
    private ArrayList<String> tabList = new ArrayList<>();
    private EditText mNewTab;
    private View mView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mShowNewTabDialog = (FloatingActionButton) findViewById(R.id.addNewTab);

//        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tabList );
//        ListView listView = (ListView) findViewById(R.id.lvTabs);
//        listView.setAdapter(mAdapter);

        try {
            String line;
            FileInputStream fis = openFileInput("tab_list");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            while ((line=br.readLine())!=null){
                tabList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //lv.setAdapter(new MyListAdapter(this,R.layout.tab_list_item,tabList));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tabList );
        final ListView listView = (ListView) findViewById(R.id.lvTabs);
        listView.setAdapter(mAdapter);
        listView.setClickable(true);
        listView.setLongClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String openTab = (String) listView.getItemAtPosition(position);
                mView = getLayoutInflater().inflate(R.layout.dialog_new_tab,null);
                addNewTab(mView,openTab);
            }

            @Override
            public void onItemLongClick(ListView l, View view, final int position, long id){
                //TODO: Allow for item deletion with long click
            }
        });

        mShowNewTabDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_new_tab,null);
                mNewTab = (EditText) mView.findViewById(R.id.editTextTabName);
                Button mNewTabGo = (Button) mView.findViewById(R.id.btAddNewTab);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();

                mNewTabGo.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view){
                        String tabName = mNewTab.getText().toString();

                        if(tabName.isEmpty()){
                            Toast.makeText(MainActivity.this, R.string.tab_error_message,Toast.LENGTH_LONG).show();
                        }else {
                            dialog.dismiss();
                            addNewTab(mView);
                        }

                    }
                });


            }
        });
    }

//    private class MyListAdapter extends ArrayAdapter<String>{
//
//        private int layout;
//
//
//        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
//            super(context, resource, objects);
//            Log.d("MyListAdapter","Entered class");
//            layout = resource;
//        }
//
//        @Override
//        public int getCount(){
//            Log.d("Main","current count: "+tabList.size());
//            return tabList.size();
//
//        }
//
//        @Override
//        public long getItemId (int position) {
//            return position;
//}
//        @Override
//        public String getItem (int position) {
//            return tabList.get(position);
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent){
//
//            MyViewHolder viewHolder = null;
//
//            if (convertView == null){
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                convertView = inflater.inflate(layout,parent,false);
//                viewHolder = new MyViewHolder();
//                viewHolder.button = (Button) convertView.findViewById(R.id.btTabSelect);
//                viewHolder.button.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        Toast.makeText(getContext(),"Button Click "+position,Toast.LENGTH_LONG);
//                    }
//                                                     });
//                convertView.setTag(viewHolder);
//            }else {
//                viewHolder = (MyViewHolder) convertView.getTag();
//                viewHolder.button.setText("test");
//            }
//            return convertView;
//        }
//    }
//
//    public class MyViewHolder{
//        Button button;
//    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            FileOutputStream fos =  openFileOutput("tab_list",MODE_PRIVATE);
            for (String tabName:tabList){
                fos.write((tabName+"\n").getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();

        try {
            String line;
            FileInputStream fis = openFileInput("tab_list");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            while ((line=br.readLine())!=null){
                tabList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ListView lv = (ListView) findViewById(R.id.lvTabs);
        //lv.setAdapter(new MyListAdapter(this,R.layout.tab_list_item,tabList));
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tabList );
        ListView listView = (ListView) findViewById(R.id.lvTabs);
        listView.setAdapter(mAdapter);
        listView.setClickable(true);

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if (isFinishing()) {
            //deleteFile("tab_list");
        }
    }

    public void addNewTab(View view, String tabName){
        Intent intent = new Intent(this,SingleTabActivity.class);
        //EditText editText = (EditText) view.findViewById(R.id.editTextTabName);
        //String message = editText.getText().toString();
        //tabList.add(message);
        //tabList.add(new Tab(tabName));
        //intent.putExtra(EXTRA_MESSAGE,message);
        intent.putExtra(EXTRA_MESSAGE, tabName);
        startActivity(intent);
    }

    public void addNewTab(View view){
        Intent intent = new Intent(this,SingleTabActivity.class);
        EditText editText = (EditText) view.findViewById(R.id.editTextTabName);
        String message = editText.getText().toString();
        tabList.add(message);
        //tabList.add(new Tab(tabName));
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}


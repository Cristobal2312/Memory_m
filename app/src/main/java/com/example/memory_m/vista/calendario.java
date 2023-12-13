package com.example.memory_m.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cristobal.memory_m.R;
import com.example.memory_m.controlador.DatabaseHelper;
import com.example.memory_m.modelo.AddActivity;
import com.example.memory_m.modelo.Alarm;
import com.example.memory_m.modelo.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class calendario extends AppCompatActivity {

    public static String activeAlarm = "";
    private static final int REQUEST_CODE = 1000;
    private ListView listView;

    public static List<Alarm> alarmList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendario.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        listView = findViewById(R.id.listView);
        alarmList.addAll(db.getAllAlarms());
        customAdapter = new CustomAdapter(getApplicationContext(), alarmList, calendario.this);
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            boolean needRefresh = data != null && data.getBooleanExtra("needRefresh", false);
            if (needRefresh) {
                updateAlarmListFromDatabase();
            }
        }
    }

    private void updateAlarmListFromDatabase() {
        alarmList.clear();
        alarmList.addAll(db.getAllAlarms());
        customAdapter.notifyDataSetChanged();
    }
}

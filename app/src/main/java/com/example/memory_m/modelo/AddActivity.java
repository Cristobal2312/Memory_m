package com.example.memory_m.modelo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memory_m.R;
import com.example.memory_m.controlador.DatabaseHelper;
import com.example.memory_m.modelo.Alarm;

public class AddActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private EditText editText;
    private Button buttonSave, buttonCancel;
    private boolean needRefresh;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        timePicker = findViewById(R.id.timePicker);
        editText = findViewById(R.id.name);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    // Antes de Android 23
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                String name = editText.getText().toString();

                DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                // Utilizar el constructor adecuado de la clase Alarm
                Alarm alarm = new Alarm(0, hour, minute, true, name); // Asignar 0 al id o utilizar el valor adecuado
                db.addAlarm(alarm);

                needRefresh = true;

                onBackPressed();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        setResult(RESULT_OK, data);
        super.finish();
    }
}

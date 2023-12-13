package com.example.memory_m.modelo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cristobal.memory_m.R;
import com.example.memory_m.controlador.DatabaseHelper;
import com.example.memory_m.vista.calendario;

import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final List<Alarm> alarmList;
    private final LayoutInflater layoutInflater;

    public CustomAdapter(Context c, List<Alarm> alarmList, calendario calendario) {
        this.context = c;
        this.alarmList = alarmList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.row_item, null);
        final Alarm selectedAlarm = alarmList.get(position);
        final TextView nameTV = convertView.findViewById(R.id.nameTextView);
        final TextView alarmTV = convertView.findViewById(R.id.timeTextView);
        final ToggleButton toggleButton = convertView.findViewById(R.id.toggle);
        final Button deleteButton = convertView.findViewById(R.id.deleteButton);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        nameTV.setText(selectedAlarm.getName());
        alarmTV.setText(selectedAlarm.toString());

        final Intent serviceIntent = new Intent(context, AlarmReceiver.class);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selectedAlarm.getHour());
        calendar.set(Calendar.MINUTE, selectedAlarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DATE, 1);
        }

        toggleButton.setChecked(selectedAlarm.getStatus());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedAlarm.setStatus(isChecked);
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateAlarm(selectedAlarm);

                calendario.alarmList.clear();
                List<Alarm> list = db.getAllAlarms();
                calendario.alarmList.addAll(list);
                notifyDataSetChanged();

                if (!isChecked && selectedAlarm.toString().equals(calendario.activeAlarm)) {
                    serviceIntent.putExtra("extra", "off");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                    alarmManager.cancel(pendingIntent);
                    context.sendBroadcast(serviceIntent);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.deleteAlarm(selectedAlarm);

                calendario.alarmList.clear();
                List<Alarm> list = db.getAllAlarms();
                calendario.alarmList.addAll(list);
                notifyDataSetChanged();
            }
        });

        if (selectedAlarm.getStatus()) {
            serviceIntent.putExtra("extra", "on");
            serviceIntent.putExtra("active", selectedAlarm.toString());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        return convertView;
    }
}

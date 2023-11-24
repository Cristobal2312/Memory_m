package com.example.memory_m.vista;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.memory_m.R;

public class MainActivity extends AppCompatActivity {

    Button Btn_Login, Btn_Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn_Login = findViewById(R.id.Btn_Login);
        Btn_Registro = findViewById(R.id.Btn_Registro);

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad de Login
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        Btn_Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad de Registro
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }
}


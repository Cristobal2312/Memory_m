package com.example.memory_m.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.memory_m.FragmentDos;
import com.example.memory_m.FragmentTres;
import com.example.memory_m.FragmentUno;
import com.example.memory_m.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottomNavigationView = findViewById(R.id.boton_navigation_View);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.fragmentUno){
                Fragmentos(new FragmentUno());
            }
            if (item.getItemId() == R.id.fragmentDos){
                Fragmentos(new FragmentDos());
            }
            if (item.getItemId() == R.id.fragmentTres){
                Fragmentos(new FragmentTres());
            }
            return true; // Cambia esto según tus necesidades, pero debe ser de tipo booleano
        }
    };
    private void Fragmentos(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}

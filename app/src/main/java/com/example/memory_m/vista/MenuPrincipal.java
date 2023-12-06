package com.example.memory_m.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.example.memory_m.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {
    private ImageView cerrarSesion;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private TextView nombresPrincipal, correoPrincipal;
    private ProgressBar progressBarDatos;
    private DatabaseReference usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("M.memory");
        }

        nombresPrincipal = findViewById(R.id.NombresPrincipal);
        correoPrincipal = findViewById(R.id.CorreoPrincipal);
        progressBarDatos = findViewById(R.id.progressDatos);
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        cerrarSesion = findViewById(R.id.CerrarSesion);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // Agregamos la quinta ImageView (remediosImageView) al OnClickListener


        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salirAplicacion();
            }
        });

        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, calendario.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, agenda.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        comprobarInicioSesion();
    }


    private void comprobarInicioSesion() {
        if (user != null) {
            cargaDeDatos();
        } else {
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            finish(); // Cerrar esta actividad para evitar volver atrás.
        }
    }

    private void cargaDeDatos() {
        usuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressBarDatos.setVisibility(View.GONE);
                    nombresPrincipal.setVisibility(View.VISIBLE);
                    correoPrincipal.setVisibility(View.VISIBLE);

                    String nombre = snapshot.child("nombre").getValue(String.class);
                    String correo = snapshot.child("correo").getValue(String.class);

                    nombresPrincipal.setText(nombre);
                    correoPrincipal.setText(correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores de Firebase aquí si es necesario.
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_botton,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.fragmentUno) {
            ActivityMenuPrincipal();
        }
        if (item.getItemId() == R.id.fragmentDos) {
            ActivityMaps();
        }
        if (item.getItemId() == R.id.fragmentTres) {
            ActivityDatosUsuarios();
        }
        return true;
    }

    private void ActivityDatosUsuarios() {
        Intent intent = new Intent(MenuPrincipal.this, DatosUsuarios.class);
        startActivity(intent);
    }

    private void ActivityMaps() {
        Intent intent = new Intent(MenuPrincipal.this, MapsActivity.class);
        startActivity(intent);
    }

    private void ActivityMenuPrincipal() {
        Intent intent = new Intent(MenuPrincipal.this, MenuPrincipal.class);
        startActivity(intent);
    }

    private void salirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        finish(); // Cerrar esta actividad para evitar volver atrás.
        Toast.makeText(this, "Cerraste sesión exitosamente", Toast.LENGTH_SHORT).show();
    }
}

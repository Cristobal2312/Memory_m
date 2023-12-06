package com.example.memory_m;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.memory_m.vista.agenda;
import com.example.memory_m.vista.calendario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentUno extends Fragment {

    private View fragmento;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference usuarios;
    private ProgressBar progressBarDatos;

    public FragmentUno() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        fragmento = inflater.inflate(R.layout.fragment_uno, container, false);

        // Inicializar Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Inicializar ProgressBar
        progressBarDatos = fragmento.findViewById(R.id.progressDatos);

        return fragmento;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Acceder a los elementos de la vista
        ImageView imageView2 = fragmento.findViewById(R.id.imageView2);
        ImageView imageView3 = fragmento.findViewById(R.id.imageView3);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad(calendario.class);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad(agenda.class);
            }
        });

        // Verificar inicio de sesión y cargar datos
        comprobarInicioSesion();
    }

    private void abrirActividad(Class<?> actividad) {
        Intent intent = new Intent(getActivity(), actividad);
        startActivity(intent);
    }

    private void comprobarInicioSesion() {
        if (user != null) {
            cargaDeDatos();
        } else {
            // Manejar la lógica si el usuario no ha iniciado sesión
            // Por ejemplo, redirigir a la actividad de inicio de sesión
        }
    }

    private void cargaDeDatos() {
        // Añadir logs para depuración
        Log.d("FragmentUno", "Cargando datos para el usuario: " + user.getUid());

        // Mostrar ProgressBar
        progressBarDatos.setVisibility(View.VISIBLE);

        usuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Ocultar ProgressBar después de cargar datos
                progressBarDatos.setVisibility(View.GONE);

                if (snapshot.exists()) {
                    // Puedes manejar los datos del usuario aquí
                    // Imprimir los datos para verificar
                    Log.d("FragmentUno", "Datos del usuario: " + snapshot.getValue());

                    // Actualizar la interfaz de usuario con los datos del snapshot
                    actualizarInterfazUsuario(snapshot);
                } else {
                    Log.d("FragmentUno", "No se encontraron datos para el usuario con ID: " + user.getUid());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Ocultar ProgressBar en caso de error
                progressBarDatos.setVisibility(View.GONE);

                // Manejar errores de Firebase aquí si es necesario.
                Log.e("FragmentUno", "Error al cargar datos: " + error.getMessage());
            }
        });
    }

    private void actualizarInterfazUsuario(DataSnapshot snapshot) {
        // Aquí puedes obtener los datos específicos del usuario desde el snapshot
        // y actualizar tu interfaz de usuario según sea necesario.
        String nombre = snapshot.child("nombre").getValue(String.class);
        String correo = snapshot.child("correo").getValue(String.class);

        // Por ejemplo, puedes actualizar los TextViews con estos datos
        TextView nombresPrincipal = fragmento.findViewById(R.id.NombresPrincipal);
        TextView correoPrincipal = fragmento.findViewById(R.id.CorreoPrincipal);

        nombresPrincipal.setText(nombre);
        correoPrincipal.setText(correo);
    }
}

package com.example.memory_m;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.memory_m.vista.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentTres extends Fragment {

    private View fragmento;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference usuarios;
    private ProgressBar progressBarDatos;
    private TextView nombresPrincipal, correoPrincipal;

    public FragmentTres() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        fragmento = inflater.inflate(R.layout.fragment_tres, container, false);

        // Inicializar Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        // Inicializar elementos de la vista
        progressBarDatos = fragmento.findViewById(R.id.progressDatos);
        nombresPrincipal = fragmento.findViewById(R.id.NombresPrincipal);
        correoPrincipal = fragmento.findViewById(R.id.CorreoPrincipal);

        // Configurar clic en la imagen para cerrar sesión
        ImageView cerrarSesion = fragmento.findViewById(R.id.CerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });

        // Verificar inicio de sesión y cargar datos
        comprobarInicioSesion();

        return fragmento;
    }

    private void cerrarSesion() {
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish(); // Cerrar la actividad actual para evitar volver atrás.
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
        // Mostrar ProgressBar
        progressBarDatos.setVisibility(View.VISIBLE);

        usuarios.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Ocultar ProgressBar después de cargar datos
                progressBarDatos.setVisibility(View.GONE);

                if (snapshot.exists()) {
                    // Actualizar la interfaz de usuario con los datos del snapshot
                    actualizarInterfazUsuario(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Ocultar ProgressBar en caso de error
                progressBarDatos.setVisibility(View.GONE);
            }
        });
    }

    private void actualizarInterfazUsuario(DataSnapshot snapshot) {
        // Aquí puedes obtener los datos específicos del usuario desde el snapshot
        String nombre = snapshot.child("nombre").getValue(String.class);
        String correo = snapshot.child("correo").getValue(String.class);

        // Actualizar TextViews con estos datos
        nombresPrincipal.setText(nombre);
        correoPrincipal.setText(correo);

        // Mostrar TextViews
        nombresPrincipal.setVisibility(View.VISIBLE);
        correoPrincipal.setVisibility(View.VISIBLE);
    }
}

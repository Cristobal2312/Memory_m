package com.example.memory_m.vista;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memory_m.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NombreEt, CorreoEt, ContraseñaEt, ConfirmarContraseñaEt;
    Button RegistroUsuario;
    TextView TengocuentaTXT;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        NombreEt = findViewById(R.id.NombreEt);
        CorreoEt = findViewById(R.id.CorreoEt);
        ContraseñaEt = findViewById(R.id.ContraseñaEt);
        ConfirmarContraseñaEt = findViewById(R.id.ConfirmarContraseñaEt);
        RegistroUsuario = findViewById(R.id.RegistroUsuario);
        TengocuentaTXT = findViewById(R.id.TengocuentaTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        RegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarDatos();
            }
        });

        TengocuentaTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la actividad de inicio de sesión
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void ValidarDatos() {
        // Obtiene los valores de los campos
        String nombre = NombreEt.getText().toString().trim();
        String correo = CorreoEt.getText().toString().trim();
        String contraseña = ContraseñaEt.getText().toString();
        String confirmarContraseña = ConfirmarContraseñaEt.getText().toString();

        // Valida que los campos no estén vacíos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(correo) ||
                TextUtils.isEmpty(contraseña) || TextUtils.isEmpty(confirmarContraseña)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida el formato del correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseña)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llama a la función de registro
        RegistrarUsuario(correo, contraseña, nombre);
    }

    private void RegistrarUsuario(String correo, String contraseña, String nombre) {

        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Registro exitoso
                        Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                        // Si deseas guardar la información del usuario en Firebase Realtime Database:
                        GuardarInformacionEnDatabase(authResult.getUser().getUid(), nombre, correo);

                        // Redirige a la actividad de inicio o realiza cualquier otra acción necesaria
                        // Ejemplo:
                        startActivity(new Intent(Registro.this, AdminActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error en el registro
                        Toast.makeText(Registro.this, "Error en el registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void GuardarInformacionEnDatabase(String userId, String nombre, String correo) {
        // Aquí debes implementar el código para guardar la información del usuario en Firebase Realtime Database.
        // Puedes crear una referencia a la base de datos y usar HashMap para almacenar los datos.

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("nombre", nombre);
        userData.put("correo", correo);

        databaseReference.child(userId).setValue(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Información del usuario guardada exitosamente
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al guardar la información del usuario
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

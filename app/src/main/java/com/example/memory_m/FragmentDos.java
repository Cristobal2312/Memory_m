package com.example.memory_m;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.memory_m.vista.MapsActivity;
import com.example.memory_m.vista.agenda;
import com.example.memory_m.vista.calendario;


public class FragmentDos extends Fragment {

    private View fragmento;

    public FragmentDos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmento = inflater.inflate(R.layout.fragment_dos, container, false);
        return fragmento;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Accede a tus elementos de la vista aquí, por ejemplo:
        ImageView imageView1 = fragmento.findViewById(R.id.imageView1);


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActividad(MapsActivity.class);
            }
        });

    }
    private void abrirActividad(Class<?> actividad) {
        Intent intent = new Intent(getActivity(), actividad);
        startActivity(intent);
    }
}
package com.ionicframework.penchoyaida233650.nosotros.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.club.ws.PostClub;
import com.ionicframework.penchoyaida233650.nosotros.activity.NosotrosDetalleActivity;
import com.ionicframework.penchoyaida233650.nosotros.activity.NosotrosListDetalleActivity;

public class FragmentNosotros extends Fragment{

    RelativeLayout perfilPencho,perfilAida,staff;
    View rootView;

    int[] foto = {R.drawable.pencho_perfil,R.drawable.aida_perfil};
    String[] nombre = {"Pencho Duque","Aída Farrar"};
    String[] twitter = {"@penchoduque","@aidafarrar"};
    String[] desc = {
            "Pencho Duque ha trabajado en medios de comunicación desde 1987. Fue director de Radio 102nueve. También se desempeño como presentador de noticias y programas televisivos y desde 2010 dirige Producciones Multimedia ('Pencho & Aída')",
            "Aída Farrar se graduó de la carrera de psicología y trabaja como presentadora de noticias en canal 6 y canal 12, entre otros. Ha conducido programas de radio en YSU y 102nueve. A finales de 2014 fue incluida en la lista de las '50 mujeres más poderosas de Centro América' de la Revista Forbes C.A."};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.nosotros, container, false);
        perfilPencho = (RelativeLayout)rootView.findViewById(R.id.perfilPencho);
        perfilPencho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), NosotrosDetalleActivity.class);
                a.putExtra("foto",foto[0]);
                a.putExtra("nombre",nombre[0]);
                a.putExtra("twitter",twitter[0]);
                a.putExtra("desc",desc[0]);
                startActivity(a);
            }
        });

        perfilAida = (RelativeLayout)rootView.findViewById(R.id.perfilAida);
        perfilAida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), NosotrosDetalleActivity.class);
                a.putExtra("foto",foto[1]);
                a.putExtra("nombre",nombre[1]);
                a.putExtra("twitter",twitter[1]);
                a.putExtra("desc",desc[1]);
                startActivity(a);
            }
        });

        staff = (RelativeLayout)rootView.findViewById(R.id.staff);
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), NosotrosListDetalleActivity.class);
                startActivity(a);
            }
        });

        return rootView;
    }


}

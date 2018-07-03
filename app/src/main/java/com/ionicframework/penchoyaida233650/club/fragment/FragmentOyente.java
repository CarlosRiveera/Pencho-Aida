package com.ionicframework.penchoyaida233650.club.fragment;







import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;



import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import com.ionicframework.penchoyaida233650.MainActivity;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.club.ws.PostClub;

public class FragmentOyente extends Fragment implements DatePickerDialog.OnDateSetListener {


    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    EditText edit;
    private ProgressDialog loading;
    PostClub post;
    EditText editnombre,edittelefono,editemail,editocupacion,conozco;
    Spinner editpais;
    SwitchCompat SWpremios,SWpromocion;

    String nombre;
    String nacimiento;
    String email ;
    String pais ;
    String profesion;
    String premios = "0";
    String promociones = "0";
    String comentarios ;
    String telefono ;
    View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.club_oyente, container, false);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());

        Locale[] locale = Locale.getAvailableLocales();
        final ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        getActivity().setTitle("");
        editpais = (Spinner) rootView.findViewById(R.id.editpais);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        editpais.setAdapter(adapter);

        editnombre = (EditText)rootView.findViewById(R.id.editnombre);
        edit = (EditText) rootView.findViewById(R.id.editfecha);
        edittelefono = (EditText) rootView.findViewById(R.id.edittelefono);
        editemail = (EditText) rootView.findViewById(R.id.editemail);
        editocupacion = (EditText) rootView.findViewById(R.id.editocupacion);
        conozco = (EditText) rootView.findViewById(R.id.conozco);
        SWpremios = (SwitchCompat) rootView.findViewById(R.id.SWpremios);
        SWpromocion = (SwitchCompat) rootView.findViewById(R.id.SWpromocion);
        final FragmentManager fragManager = getActivity().getFragmentManager();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = DatePickerDialog.newInstance(FragmentOyente.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dpd.setMaxDate(calendar);
                dpd.show(getActivity().getFragmentManager(), "datePicker");

            }
        });

        SWpremios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    premios = "1";
                }else{
                    premios = "0";
                }
            }
        });

        SWpromocion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    promociones = "1";
                }else{
                    promociones = "0";
                }
            }
        });

        Button btnEnviar = (Button)rootView.findViewById(R.id.enviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = editnombre.getText().toString();
                nacimiento = edit.getText().toString();
                email = editemail.getText().toString();
                pais = editpais.getSelectedItem().toString();;
                profesion = editocupacion.getText().toString();
                comentarios = conozco.getText().toString();
                telefono = edittelefono.getText().toString();
                if(nombre.equals("")){
                    Toast.makeText(getActivity(), getResources().getString(R.string.escriban), Toast.LENGTH_SHORT).show();
                }else if(nacimiento.equals("")){
                    Toast.makeText(getActivity(), getResources().getString(R.string.escrinafe), Toast.LENGTH_SHORT).show();
                }else if(email.equals("")){
                    Toast.makeText(getActivity(), getResources().getString(R.string.escribacorre), Toast.LENGTH_SHORT).show();
                }else{
                    new Task(nombre,nacimiento,email,pais,profesion,premios,promociones,comentarios,telefono).execute();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        edit.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
    }

    public class Task extends AsyncTask<Void, Void, Void> {

        int resultado;
        String nombre,nacimiento,email,pais,profesion,premios,promociones,comentarios,telefono;

        public Task(String nombre,String nacimiento,String email,String pais,String profesion,String premios,String promociones,String comentarios,String telefono) {
            this.nombre = nombre;
            this.nacimiento = nacimiento;
            this.email = email;
            this.pais = pais;
            this.profesion = profesion;
            this.premios = premios;
            this.promociones = promociones;
            this.comentarios = comentarios;
            this.telefono = telefono;
        }

        @Override
        protected void onPreExecute() {
            try {
                loading = new ProgressDialog(getActivity());
                loading.setMessage(getResources().getString(R.string.cargando));
                loading.setCancelable(false);
                loading.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            post = new PostClub(nombre,nacimiento,email,pais,profesion,premios,promociones,comentarios,telefono);
            post.PostSolicitud(getActivity());
            resultado = post.result;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loading.dismiss();
            if(resultado == 0){
                editnombre.setText("");
                edit.setText("");
                edittelefono.setText("");
                editemail.setText("");
                editocupacion.setText("");
                editpais.setTop(0);
                conozco.setText("");
                Toast.makeText(getActivity(), getResources().getString(R.string.registro), Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Intent a = new Intent(getActivity(), MainActivity.class);
                a.putExtra("valor","0");
                startActivity(a);
            }else{
                Toast.makeText(getActivity(), getResources().getString(R.string.ocurrio), Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(aVoid);
        }
    }
}

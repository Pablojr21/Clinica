package com.example.appclinicabiovida;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menuPaciente extends AppCompatActivity  {


    TextView tvPacienteIniaciadoSeccion;
    SharedPreferences preDatosCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvPacienteIniaciadoSeccion=findViewById(R.id.tvPacienteIniciadoSeccion);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");


        // Verifica si el nombre y el apellido son válidos y establece el texto
        if (nombre != null && apellido != null) {
            tvPacienteIniaciadoSeccion.setText("Bienvenido " + nombre + " " + apellido);
        } else {
            tvPacienteIniaciadoSeccion.setText("Bienvenido"); // Mensaje por defecto
        }




    }

    public void registrarcita(View v)
    {
           Intent objIntent = new Intent(menuPaciente.this,elegirEspecilidad.class);
           startActivity(objIntent);
           finish();

    }

    public void anularCita(View v)
    {
        Intent objIntent = new Intent(menuPaciente.this, AnularCita.class);
        startActivity(objIntent);
        finish();

    }

    public void mostrarCitasRegistradas(View v)
    {
        Intent objIntent = new Intent(menuPaciente.this,mostrarcitasPaciente.class);
        startActivity(objIntent);
        finish();

    }
}
package com.example.appclinicabiovida;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class inicioSeccionPaciente extends AppCompatActivity implements View.OnClickListener {
    ImageView btnRegresarISP;
    TextView txvRegistrarpaciente;
    TextInputEditText tietingresardnipaciente, tietingresarpasspaciente;
    Button btnIniciarSeccionPaciente;
    SharedPreferences preDatosCita;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_seccion_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        preDatosCita = getSharedPreferences("preDatosCita", MODE_PRIVATE);

         btnRegresarISP = findViewById(R.id.btnRegresarISP);
         btnRegresarISP.setOnClickListener(this);
        
         txvRegistrarpaciente = findViewById(R.id.txvRegistrarPaciente);
         txvRegistrarpaciente.setOnClickListener(this);

         tietingresardnipaciente=findViewById(R.id.tietIngresardnipaciente);
         tietingresarpasspaciente=findViewById(R.id.tietIngresarpasspaciente);

         btnIniciarSeccionPaciente=findViewById(R.id.btnIngresarPaciente);


        // Filtro para solo permitir números en DNI
        InputFilter soloNumeros = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";  // Rechaza caracteres que no sean dígitos
                    }
                }
                return null;
            }
        };

        tietingresardnipaciente.setFilters(new InputFilter[]{soloNumeros, new InputFilter.LengthFilter(8)});
    }



    private boolean validarCamposVacios(String dni, String password) {
        if (dni.isEmpty()) {
            Toast.makeText(this, "Ingrese DNI", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if (v==btnRegresarISP)
        {
            Intent objIntent= new Intent(inicioSeccionPaciente.this,MainActivity.class);
            startActivity(objIntent);
            finish();

        }
         if (v==txvRegistrarpaciente)
        {
            Intent objIntent= new Intent(inicioSeccionPaciente.this,registrarPaciente.class);
            startActivity(objIntent);
            finish();

        }

    }
    public boolean validardni(String dni)
    {
        if (dni.length() != 8) {
            Toast.makeText(this, "El dni debe tener 8 digitos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validarPassword(String password)
    {
        if (password.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 digitos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public  void iniciarseccionPaciente(View view)
    {
            try {
                String dni_paciente = tietingresardnipaciente.getText().toString().trim();
                String password_paciente = tietingresarpasspaciente.getText().toString().trim();

                if (validarCamposVacios(dni_paciente, password_paciente) && validardni(dni_paciente) && validarPassword(password_paciente)) {
                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Cargando...");
                    progressDialog.show();

                    StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.7:80/paciente/login_.php",
                            response -> {
                                progressDialog.dismiss();
                                try {

                                    JSONObject jsonResponse = new JSONObject(response);
                                    String status = jsonResponse.getString("status");

                                    if (status.equalsIgnoreCase("login exitoso")) {
                                        String nombre = jsonResponse.getString("nombre");
                                        String apellido = jsonResponse.getString("apellido");


                                        // Guardar datos en SharedPreferences
                                        SharedPreferences.Editor editor = preDatosCita.edit();
                                        editor.putString("nombrePaciente", nombre);
                                        editor.putString("apellidoPaciente", apellido);
                                        editor.putString("dniPaciente",dni_paciente);  // Asegúrate de que 'dniPaciente' sea String
                                        editor.apply();

                                        Intent intent = new Intent(inicioSeccionPaciente.this, menuPaciente.class);
                                        intent.putExtra("nombre", nombre);
                                        intent.putExtra("apellido", apellido);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(inicioSeccionPaciente.this, "DNI o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(inicioSeccionPaciente.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                                }
                            }, error -> {
                        progressDialog.dismiss();
                        Toast.makeText(inicioSeccionPaciente.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("dni_paciente", dni_paciente);
                            params.put("password_paciente", password_paciente);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(inicioSeccionPaciente.this);
                    requestQueue.add(request);
                }

            } catch (Exception ex) {
                Toast.makeText(this, "Error al intentar iniciar sesión. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show();
            }

    }
}



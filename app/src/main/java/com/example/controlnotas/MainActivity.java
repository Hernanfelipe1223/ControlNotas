package com.example.controlnotas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText  et2, et3, et4,et5;
    Button b1;
    TextView tvPerdieron,tvResultado,tvNumEstudiantes;
    CheckBox cb1;
    RadioButton rbAlumno,rbProfesor;
    private int contadorEstudiantesPerdieron = 0;
    double notaDefinitiva = 0;
    private static final String SHARED_PREF_NAME = "MiSharedPreferences";
    private static final String TV_PERDIERON_KEY = "tvPerdieron";
    int estudiantesPerdieron = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        b1 = findViewById(R.id.b1);
        tvPerdieron = findViewById(R.id.tvPerdieron);
        tvResultado = findViewById(R.id.tvResultado);
        cb1 = findViewById(R.id.cb1);
        rbAlumno = findViewById(R.id.radioButtonAlumno);
        rbProfesor = findViewById(R.id.radioButtonProfesor);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        et2.setText(preferences.getString("text2", ""));
        et3.setText(preferences.getString("text3", ""));
        et4.setText(preferences.getString("text4", ""));
        et5.setText(preferences.getString("text5", ""));
        tvPerdieron.setText(preferences.getString("tvPerdieron", ""));
        tvResultado.setText(preferences.getString("tvResultado", ""));

        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                calcularNota();
            }
        });
    }

    private void calcularNota() {
            String nota1Text = et2.getText().toString();
            String nota2Text = et3.getText().toString();
            String nota3Text = et4.getText().toString();
            String nota4Text = et5.getText().toString();
            if (nota1Text.isEmpty() || nota2Text.isEmpty() || nota3Text.isEmpty() || nota4Text.isEmpty()) {
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
                return;
            }
             if (esNumero(nota1Text) && esNumero(nota2Text) && esNumero(nota3Text) && esNumero(nota4Text)) {
                 double nota1 = Double.parseDouble(nota1Text);
                 double nota2 = Double.parseDouble(nota2Text);
                 double nota3 = Double.parseDouble(nota3Text);
                 double nota4 = Double.parseDouble(nota4Text);

                 if (rbProfesor.isChecked()) {
                     notaDefinitiva = (nota1 * 0.20 + nota2 * 0.30 + nota3 * 0.15 + nota4 * 0.35);
                     tvResultado.setText("Nota Final: " + notaDefinitiva);
                     if (nota1 < 0 || nota1 > 5 || nota2 < 0 || nota2 > 5 || nota3 < 0 || nota3 > 5 || nota4 < 0 || nota4 > 5) {
                         Toast.makeText(this, "Las notas deben estar entre 0 y 5", Toast.LENGTH_SHORT).show();
                         limpiarCampos();
                         return;
                     }
                     notaDefinitiva = (nota1 * 0.20) + (nota2 * 0.30) + (nota3 * 0.15) + (nota4 * 0.35);
                     tvResultado.setText("Nota Definitiva: " + notaDefinitiva);
                     if (notaDefinitiva < 3.0) {
                         estudiantesPerdieron++;
                         tvPerdieron.setText("Estudiantes que perdieron: " + estudiantesPerdieron);
                         guardar();
                         limpiarCampos();
                     } else {
                         guardar();
                         limpiarCampos();
                     }
                 }
                 else {
                     notaDefinitiva = (nota1 * 0.20) + (nota2 * 0.30) + (nota3 * 0.15) + (nota4 * 0.35);
                     Toast.makeText(this, "Selecciono alumno, solo se mostrara el promedio del alumno", Toast.LENGTH_SHORT).show();
                     tvResultado.setText("Nota Definitiva: " + notaDefinitiva);
                     guardar();
                     limpiarCampos();
                 }
             }
             else {
                     Toast.makeText(this, "ingrese valores numericos  ", Toast.LENGTH_SHORT).show();
                     limpiarCampos();
             }
             if(cb1.isChecked()){
                 limpiarCampos();
                 tvResultado.setText("");
                 tvPerdieron.setText("");
                 cb1.setChecked(false);
                 guardar();
             }
    }

    private void guardar() {
        SharedPreferences preferencias = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferencias.edit();
        obj_editor.putString("text2", et2.getText().toString());
        obj_editor.putString("text3", et3.getText().toString());
        obj_editor.putString("text4", et4.getText().toString());
        obj_editor.putString("text5", et5.getText().toString());
        obj_editor.putString("tvPerdieron", tvPerdieron.getText().toString());
        obj_editor.putString("tvResultado", tvResultado.getText().toString());
        obj_editor.commit();
        Toast.makeText(this, "Se Ha guardado los cambios", Toast.LENGTH_LONG).show();
    }
    private void limpiarCampos() {
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
    }

    private boolean esNumero(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}

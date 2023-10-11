package com.example.controlnotas;

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

    EditText et1, et2, et3, et4,et5;
    Button b1;
    TextView tvPerdieron,tvResultado,tvNumEstudiantes;
    CheckBox cb1;

    RadioButton rbN,rbY;

    private int contadorEstudiantesPerdieron = 0;
    double notaDefinitiva = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        b1 = findViewById(R.id.b1);
        tvPerdieron = findViewById(R.id.tvPerdieron);
        tvResultado = findViewById(R.id.tvResultado);
        tvNumEstudiantes = findViewById(R.id.tvNumEstudiantes);
        cb1 = findViewById(R.id.cb1);
        rbN = findViewById(R.id.rbN);
        rbY = findViewById(R.id.rbY);

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                calcularNota();
            }
             });
        }

        private void calcularNota() {
            String numEstudiantesText = et1.getText().toString();


            if (numEstudiantesText.isEmpty()) {
                Toast.makeText(this, "Debes ingresar el número de estudiantes", Toast.LENGTH_SHORT).show();
                return;
            }
            String nota1Text = et2.getText().toString();
            String nota2Text = et3.getText().toString();
            String nota3Text = et4.getText().toString();
            String nota4Text = et5.getText().toString();


            if (nota1Text.isEmpty() || nota2Text.isEmpty() || nota3Text.isEmpty() || nota4Text.isEmpty()) {
                Toast.makeText(this, "Debes ingresar todas las notas", Toast.LENGTH_SHORT).show();
                return;
            }


            double numEstudiantes = Double.parseDouble(et1.getText().toString());
            double nota1 = Double.parseDouble(et2.getText().toString());
            double nota2 = Double.parseDouble(et3.getText().toString());
            double nota3 = Double.parseDouble(et4.getText().toString());
            double nota4 = Double.parseDouble(et5.getText().toString());

            SharedPreferences preferences = getSharedPreferences("contadorNumEstudiantes", MODE_PRIVATE);
            et1.setText(String.valueOf(preferences.getInt("numEstudiantes", 0)));
            tvResultado.setText("Nota Definitiva: " + notaDefinitiva);
            et2.setText("");
            et3.setText("");
            et4.setText("");
            et5.setText("");


            et1.setEnabled(false);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calcularNumEstudiantes();
                }
            });


            if (nota1 < 0 || nota1 > 5 || nota2 < 0 || nota2 > 5 || nota3 < 0 || nota3 > 5 || nota4 < 0 || nota4 > 5) {
                Toast.makeText(this, "Las notas deben estar entre 0 y 5", Toast.LENGTH_SHORT).show();
                return;
            }


           notaDefinitiva = (nota1 * 0.20) + (nota2 * 0.30) + (nota3 * 0.15) + (nota4 * 0.35);




            SharedPreferences perdieron = getSharedPreferences("perdieron", MODE_PRIVATE);
            contadorEstudiantesPerdieron = preferences.getInt("contadorEstudiantesPerdieron", 0);

            int estudiantesPerdieron = (notaDefinitiva < 3.0) ? 1 : 0;
            tvPerdieron.setText("Estudiantes que perdieron: " + estudiantesPerdieron);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calcularNotaDefinitiva();
                }
            });



        }



        private void calcularNumEstudiantes() {

            double numEstudiantes = Double.parseDouble(et1.getText().toString());


            SharedPreferences preferences = getSharedPreferences("contadorNumEstudiantes", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("numEstudiantes", (int) numEstudiantes);
            editor.apply();
        }
        private void calcularNotaDefinitiva() {


            int estudiantesPerdieron = (notaDefinitiva < 3.0) ? 1 : 0;


            contadorEstudiantesPerdieron += estudiantesPerdieron;


            SharedPreferences preferences = getSharedPreferences("perdieron", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("contadorEstudiantesPerdieron", contadorEstudiantesPerdieron);
            editor.apply();

            tvPerdieron.setText("Estudiantes que perdieron: " + contadorEstudiantesPerdieron);

            tvResultado.setText("Nota Definitiva: " + notaDefinitiva);
        }





    }

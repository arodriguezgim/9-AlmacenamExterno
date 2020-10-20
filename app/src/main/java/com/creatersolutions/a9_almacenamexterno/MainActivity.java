package com.creatersolutions.a9_almacenamexterno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etContenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = (EditText) findViewById(R.id.et_Nombre);
        etContenido = (EditText) findViewById(R.id.et_contenido);

    }


    // Método para el boton Guardar
    public void Guardar (View view){

        String nombre = etNombre.getText().toString();
        String contenido = etContenido.getText().toString();

        try {

            File tarjetaSD = getExternalFilesDir(null);
            //File tarjetaSD = Environment.getExternalStorageDirectory();
            Toast.makeText(this, tarjetaSD.getPath(),Toast.LENGTH_SHORT).show();
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);

            //Abrimos el archivo para poder escribir dentro de el
            OutputStreamWriter crearArchivo = new OutputStreamWriter( openFileOutput(nombre, Activity.MODE_PRIVATE));

            crearArchivo.write(contenido);
            crearArchivo.flush();
            crearArchivo.close();

            Toast.makeText(this, "Guardado correctamente",Toast.LENGTH_SHORT).show();


        } catch (IOException e){
            Toast.makeText(this, "No se pudo guardar",Toast.LENGTH_SHORT).show();
        }
    }

    public void Consultar(View view){
        String nombre = etNombre.getText().toString();

        try {
            File tarjetaSD = getExternalFilesDir(null);
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombre));

            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            String contenidoCompleto = "";

            while (linea != null){
                contenidoCompleto = contenidoCompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }

            leerArchivo.close();
            abrirArchivo.close();
            etContenido.setText(contenidoCompleto);

        } catch (IOException e) {
            Toast.makeText(this, "Error al buscar el archivo o archivo no encontrado",Toast.LENGTH_SHORT).show();
        }
    }
}
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

        //Creamoslas variables para ambos campos
        String nombre = etNombre.getText().toString();
        String contenido = etContenido.getText().toString();

        //los metodos que vamos a usar requieren try catch
        try {

            //Guardamos de manera temporalla ruta donde esta la tarjeta SD
            File tarjetaSD = getExternalFilesDir(null);
            //File tarjetaSD = Environment.getExternalStorageDirectory(); esta deprecated

            //Vemos dónde vamos a guardar nuestro archivo
            Toast.makeText(this, tarjetaSD.getPath(),Toast.LENGTH_SHORT).show();

            // Creamos el objeto ruta Archivo y le damos estos dos metodos
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);

            //Abrimos el archivo para poder escribir dentro de el.
            OutputStreamWriter crearArchivo = new OutputStreamWriter( openFileOutput(nombre, Activity.MODE_PRIVATE));

            //Tenemos que indicar al priogramam que queremos escribir en el
            crearArchivo.write(contenido);
            //limpiamos el buffer
            crearArchivo.flush();
            //Cerramos el archivo que hemos abierto
            crearArchivo.close();
            Toast.makeText(this, "Guardado correctamente",Toast.LENGTH_SHORT).show();

            etNombre.setText("");
            etContenido.setText("");


        } catch (IOException e){
            Toast.makeText(this, "No se pudo guardar",Toast.LENGTH_SHORT).show();
        }
    }

    //Creamos el metodo para el boton Buscar
    public void Consultar(View view){

        //Creamos una variable para recuperar el nombre del archivo que el usuario quiere consultar
        String nombre = etNombre.getText().toString();

        try {
            //Obtenemos la ruta de la tarjeta SD
            File tarjetaSD = getExternalFilesDir(null);
            //Vamos a buscar al archivo
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            //Abrimos el archivo con el nombre del archivo que queremos abrir
            InputStreamReader abrirArchivo = new InputStreamReader(openFileInput(nombre));

            //Vamos a ir leyendo linea por linea para ir poniendo t_odo dentro de un objeto
            BufferedReader leerArchivo = new BufferedReader(abrirArchivo);
            String linea = leerArchivo.readLine();
            //volvemos a inicializar esta variable
            String contenidoCompleto = "";

            while (linea != null){
                //Vamos leyendo y vamos guardando linea por linea
                contenidoCompleto = contenidoCompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }

            leerArchivo.close();
            //Le decimos a nuestro archivo que se tiene que cerrar
            abrirArchivo.close();
            etContenido.setText(contenidoCompleto);

        } catch (IOException e) {
            Toast.makeText(this, "Error al buscar el archivo o archivo no encontrado",Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.jumphop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;


/**
 * Esta clase define el contenido que habrá en la pantalla Menú princiapl
 * @author Diego Figueroa González
 */
public class EscenaMenuPrincipal extends Escena implements SensorEventListener {

    //Campos de la clase utilizados para pintar
    Paint pMenu;
    Bitmap fondodia=escalaAnchura("fondos/cielodia.png",anchoPantalla*2);
    Bitmap fondonoche=escalaAnchura("fondos/cielonoche.png",anchoPantalla*2);
    Bitmap imagenJuego = escalaAnchura("botones/gamepad.png", anchoPantalla / 6);
    Bitmap imagenJuegoBeta = escalaAnchura("botones/gamepadb.png", anchoPantalla / 6);
    Bitmap imagenOpciones = escalaAnchura("botones/auto-repair.png", anchoPantalla / 6);
    Bitmap imagenCreditos = escalaAnchura("botones/film-projector.png", anchoPantalla / 6);
    Bitmap imagenAyuda = escalaAnchura("botones/info.png", anchoPantalla / 6);
    Bitmap imagenRecords = escalaAnchura("botones/medallist.png", anchoPantalla / 6);

    //Campos utilizados para las colisiones
    Rect btnJuego,btnJuegoBeta, btnOpciones, btnCreditos, btnAyuda, btnRecords, btnSalir;

    //Campos utilizados para los sensores de luz
    SensorManager sensorManager;
    Sensor sensorLuz;
    float luz=0;

    //Campos utilizados para el Audio
    MediaPlayer songBase;
    AudioManager audioManager;
    int v=0;

    /**
     * Constructor para las opciones el menú y su buena interacción con las otras escenas
     * @param idEscena int de escena actual
     * @param context context de la aplicación
     * @param anchoPantalla int numero de pixeles de ancho que tiene la pantalla del dispositivo
     * @param altoPantalla int numero de pixeles de alto que tiene la pantalla del dispositivo
     */
    public EscenaMenuPrincipal(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        pMenu = new Paint();
        pMenu.setColor(Color.BLACK);
        btnJuego = new Rect(anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 6 - 50, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth(), (altoPantalla - imagenJuego.getHeight() * 6 - 50) + imagenJuego.getHeight());
        btnJuegoBeta = new Rect((anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth()+10, altoPantalla - imagenJuego.getHeight() * 6 - 50, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth()*2+10, (altoPantalla - imagenJuego.getHeight() * 6 - 50) + imagenJuego.getHeight());
        btnOpciones = new Rect(anchoPantalla / 2 - imagenOpciones.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 4 - 30, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth(), (altoPantalla - imagenJuego.getHeight() * 4 - 30) + imagenJuego.getHeight());
        btnCreditos = new Rect(anchoPantalla / 2 - imagenCreditos.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 3 - 20, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth(), (altoPantalla - imagenJuego.getHeight() * 3 - 20) + imagenJuego.getHeight());
        btnAyuda = new Rect(anchoPantalla / 2 - imagenAyuda.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 2 - 10, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth(), (altoPantalla - imagenJuego.getHeight() * 2 - 10) + imagenJuego.getHeight());
        btnRecords = new Rect(anchoPantalla / 2 - imagenRecords.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 5 - 40, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth(), (altoPantalla - imagenJuego.getHeight() * 5 - 40) + imagenJuego.getHeight());
        sensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        SharedPreferences prefOptions =context.getSharedPreferences("options",Context.MODE_PRIVATE);

        sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        audioManager=(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        v= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        songBase=MediaPlayer.create(context,R.raw.menusong);
        if(prefOptions.getBoolean("volum",true)){
            songBase.setVolume(v/2,v/2);
            songBase.setLooping(true);
            songBase.start();
        }
    }

    /**
     * Método ejecutado por Hilo en Juego que dibuja fondo de pantalla y las  opciones del menú
     * @param c Canvas sobre el que se dibuja
     */
    public void dibuja(Canvas c) {
        Log.i("Luz",""+luz);
        if(luz<2){
            c.drawBitmap(fondonoche,0,0,null);
        }else{
            c.drawBitmap(fondodia,0,0,null);
        }
        c.drawBitmap(imagenJuego, anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 6 - 50, null);
        c.drawBitmap(imagenJuegoBeta, (anchoPantalla / 2 - imagenJuego.getWidth() / 2) + imagenJuego.getWidth()+10, altoPantalla - imagenJuego.getHeight() * 6 - 50, null);
        c.drawBitmap(imagenRecords, anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 5 - 40, null);
        c.drawBitmap(imagenOpciones, anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 4 - 30, null);
        c.drawBitmap(imagenCreditos, anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 3 - 20, null);
        c.drawBitmap(imagenAyuda, anchoPantalla / 2 - imagenJuego.getWidth() / 2, altoPantalla - imagenJuego.getHeight() * 2 - 10, null);
    }

    /**
     * Método que cambia la escena al apretar una opción del menú
     * @param event MotionEvent del que obtenemos el X e Y de la pulsación
     * @return int idescena pulsada
     */
    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i("idEscenaMenuBase", idEscena + "");
        if (btnJuego.contains(x, y)) {
            Log.i("idEscenaMenu+1", idEscena + 1 + "");
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 1;
        }
        if(btnJuegoBeta.contains(x,y)){
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 2;
        }
        if(btnRecords.contains(x,y)){
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 3;
        }
        if(btnOpciones.contains(x,y)){
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 4;
        }
        if(btnCreditos.contains(x,y)){
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 5;
        }
        if(btnAyuda.contains(x,y)){
            if (sensorLuz!=null) sensorManager.unregisterListener(this);
            songBase.stop();
            return idEscena + 6;
        }
        return idEscena;
    }

    /**
     * Método reescala Bitmap a un nuevo ancho imagen
     * @param rutaAssets string ruta imagen en assets
     * @param nuevoAncho int numero pixeles del ancho deseado
     * @return Bitmap imagen con nuevo ancho
     */
    public Bitmap escalaAnchura(String rutaAssets, int nuevoAncho) {
        Bitmap bitmapAux=getBitmapFromAssets(rutaAssets);
        if (nuevoAncho==bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) /
                bitmapAux.getWidth(), true);
    }

    /**
     * Métpdp cambia el valor de luz al ultimo detectado por el sensor
     * @param event SensorEvent del sensor de luz del que obtenemos nuevo valor de luz
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
         luz = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

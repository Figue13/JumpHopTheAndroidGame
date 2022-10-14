package com.example.jumphop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Clase que contiene la pantalla recoords
 * @author Diego Figueroa González
 */
public class EscenaRecords extends Escena {
    /**
     * Fuente usada en la aplicación
     */
    Fuente fuente;
    /**
     * Pincel que dibuja records
     */
    Paint paintRecords = new Paint();
    /**
     * Pincel que dibuja el fondo
     */
    Paint paintFondo = new Paint();
    /**
     * Pincel que dibuja titulo
     */
    Paint paintTitulo=new Paint();

    /**
     * Contructor que inicializa la escena records
     * @param idEscena int escena a iniciar
     * @param context context actual del dispositivo
     * @param anchoPantalla int pixeles ancho pantalla del dispotivo
     * @param altoPantalla int pixeles alto pantalla del dispotivo
     */
    public EscenaRecords(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        paintTitulo.setTextSize(anchoPantalla/9);
        paintRecords.setTextSize(anchoPantalla/15);
    }
    /**
     * Método que dibuja en el lienzo los elementos que componen EscenaRecords
     * @param c Canvas sobre el que se dibuja
     */
    @Override
    public void dibuja(Canvas c) {
        super.dibuja(c);
        fuente= new Fuente(context);
        paintRecords.setTypeface(fuente.getFuente());
        paintRecords.setColor(Color.CYAN);
        paintFondo.setColor(Color.BLACK);
        c.drawRect(0,0,anchoPantalla,altoPantalla,paintFondo);
        paintTitulo.setTypeface(fuente.getFuente());
        paintTitulo.setColor(Color.GRAY);
        c.drawText(context.getResources().getString(R.string.Records)+"!",anchoPantalla/3,paintTitulo.getTextSize(),paintTitulo);
        SharedPreferences prefScore1 =context.getSharedPreferences("ranking",Context.MODE_PRIVATE);
        c.drawText("1- "+prefScore1.getInt("record1",0000),paintRecords.getTextSize()*5,altoPantalla/3
                +paintRecords.getTextSize(), paintRecords);
        paintRecords.setColor(Color.YELLOW);
        c.drawText("2- "+prefScore1.getInt("record2",0000),paintRecords.getTextSize()*5,altoPantalla/3
                +paintRecords.getTextSize()*2, paintRecords);
        paintRecords.setColor(Color.RED);
        c.drawText("3- "+prefScore1.getInt("record3",0000),paintRecords.getTextSize()*5,altoPantalla/3+
                paintRecords.getTextSize()*3, paintRecords);
    }

    /**
     * Método detecta pulsacion
     * @param event MotionEvent
     * @return idEscena escenaPrincipal
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        return idEscena-3;
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

}

package com.example.jumphop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Esta clase define el contenido de la escena de información del juego
 * @author Diego Figueroa González
 */
public class EscenaInfo extends Escena {
    /**
     * Pincel de dibujado
     */
    Paint paint=new Paint();
    /**
     * Imagen a dibujar
     */
    Bitmap fondo=escalaAnchura("fondos/controls.jpg",anchoPantalla);

    /**
     * Constructor que inicializa la escena Información
     * @param idEscena int escena a mostrar
     * @param context context actual
     * @param anchoPantalla int pinxeles de ancho de pantalla del dispositivo
     * @param altoPantalla int pinxeles de alto de pantalla del dispositivo
     */
    public EscenaInfo(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        paint.setColor(Color.BLACK);
    }

    /**
     * Método que dibuja el contenido de información
     * @param c Canvas lienzo sobre el que se dibuja
     */
    @Override
    public void dibuja(Canvas c) {
        c.drawRect(0,0,anchoPantalla,altoPantalla,paint);
        c.drawBitmap(fondo,0,0,null);
    }

    /**
     * Método detecta pulsación
     * @param event MotionEvent
     * @return int 0 indica escena
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        return 0;
    }
}

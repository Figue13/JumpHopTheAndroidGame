package com.example.jumphop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;


/**
 * Esta clase define el contenido que habrá en la pantalla Opciones
 * @author Diego Figueroa González
 */
public class EscenaOpciones extends Escena {
    /**
     * Imagen de opción de sonido activada
     */
    Bitmap imagenSound=escalaAnchura("botones/sound.png", anchoPantalla / 4);
    /**
     * Imagen de opción de sonido desactivada
     */
    Bitmap imagenSoundOff=escalaAnchura("botones/soundoff.png", anchoPantalla / 4);
    /**
     * Imagen de opción de vibración activada
     */
    Bitmap imagenVibrating=escalaAnchura("botones/vibrating.png", anchoPantalla / 4);
    /**
     * Imagen de opción de vibración desactivada
     */
    Bitmap imagenVibratingOff=escalaAnchura("botones/vibratingoff.png", anchoPantalla / 4);
    /**
     * Imagen salir al menú
     */
    Bitmap imagenSalir=escalaAnchura("botones/leaveplay.png", anchoPantalla / 6);
    /**
     * Condiciones de las opciones
     */
    boolean bolSound=false,bolVibrating=false;
    /**
     * Rectangulos de los botones
     */
    Rect btnSound,btnVibrating,btnSalir;

    /**
     * Constructor que inicia la escena de opciones y sus atributos
     * @param idEscena int escena a mostrar
     * @param context context actual de la aplicación
     * @param anchoPantalla int numero pixeles de ancho del dispositivo
     * @param altoPantalla int numero pixeles de alto del dispositivo
     */
    public EscenaOpciones(int idEscena, Context context, int anchoPantalla, int altoPantalla) {
        super(idEscena, context, anchoPantalla, altoPantalla);
        SharedPreferences prefOptions =context.getSharedPreferences("options",Context.MODE_PRIVATE);
        if(prefOptions.getBoolean("volum",true)){
            bolSound=true;
        }
        if(prefOptions.getBoolean("vibrating",true)){
            bolVibrating=true;
        }
        btnVibrating=new Rect(anchoPantalla/5+imagenSound.getWidth()+10,altoPantalla/2,anchoPantalla/5+imagenSound.getWidth()+10+imagenSound.getWidth(),altoPantalla/2+imagenSound.getHeight());
        btnSound=new Rect(anchoPantalla/5,altoPantalla/2,anchoPantalla/5+imagenSound.getWidth(),altoPantalla/2+imagenSound.getHeight());
        btnSalir=new Rect(anchoPantalla/2-imagenSalir.getWidth()/2,10,anchoPantalla/2+imagenSalir.getWidth(),10+imagenSalir.getHeight());
    }


    /**
     * Método que dibuja en el lienzo los elementos que componen EscenaOpciones
     * @param c Canvas sobre el que se dibuja
     */
    @Override
    public void dibuja(Canvas c) {
        if(bolVibrating){
            c.drawBitmap(imagenVibrating, anchoPantalla/5+imagenSound.getWidth()+10,altoPantalla/2,null);
        }else{
            c.drawBitmap(imagenVibratingOff, anchoPantalla/5+imagenSound.getWidth()+10,altoPantalla/2,null);
        }
        if(bolSound){
            c.drawBitmap(imagenSound, anchoPantalla/5,altoPantalla/2,null);
        }else{
            c.drawBitmap(imagenSoundOff, anchoPantalla/5,altoPantalla/2,null);
        }
       c.drawBitmap(imagenSalir,anchoPantalla/2-imagenSalir.getWidth()/2,10,null);


    }

    /**
     * Método detecta las pulsaciones en pantalla
     * @param event MotionEvent
     * @return idEscena int escena deseada
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        int index=event.getActionIndex();
        int x,y;
        int accion=event.getActionMasked();
        switch (accion){

            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX(index);
                y = (int) event.getY(index);

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                x = (int) event.getX(index);
                y = (int) event.getY(index);
                if(btnVibrating.contains(x,y)){
                    if(bolVibrating){
                        bolVibrating=false;
                    }else{
                        bolVibrating=true;
                    }
                }
                if(btnSound.contains(x,y)){
                    if(bolSound){
                        bolSound=false;
                    }else{
                        bolSound=true;
                    }
                }
                if(btnSalir.contains(x,y)){
                    SharedPreferences prefOptions =context.getSharedPreferences("options",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefOptions.edit();
                    if(bolSound){
                        editor.putBoolean("volum",true);
                    }else{
                        editor.putBoolean("volum",false);
                    }
                    if(bolVibrating){
                        editor.putBoolean("vibrating",true);
                    }else{
                        editor.putBoolean("vibrating",false);
                    }
                    editor.commit();
                    return idEscena-4;
                }
                break;

        }
        return idEscena;
    }

    /**
     * Método reescala Bitmap a un nuevo ancho imagen
     * @param rutaAssets string ruta imagen en assets
     * @param nuevoAncho int numero pixeles del ancho deseado
     * @return Bitmap imagen con nuevo ancho
     */
    @Override
    public Bitmap escalaAnchura(String rutaAssets, int nuevoAncho) {
        return super.escalaAnchura(rutaAssets, nuevoAncho);
    }
}

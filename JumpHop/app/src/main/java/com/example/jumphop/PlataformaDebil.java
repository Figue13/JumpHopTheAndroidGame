package com.example.jumphop;

import android.graphics.Bitmap;

/**
 * Esta clase define las plataformas de madera "debiles" y su metodo
 * @author Diego Figueroa González
 */
public class PlataformaDebil extends Plataforma{
    /**
     * Imagen de plataforma dañada
     */
    public Bitmap imagenDanhada;
    /**
     * Condición de plataforma al caer
     */
    public boolean caida=false;

    /**
     * Contructor para las plataformas debiles
     * @param imagen Bitmap imagen base
     * @param imagenDanhada Bitmap imagen dañada
     * @param x float posicion x
     * @param y float posicion y
     * @param altoPantalla int pixeles del alto de pantalla del dispositivo
     */
    public PlataformaDebil(Bitmap imagen, Bitmap imagenDanhada, float x, float y, int altoPantalla) {
        super(imagen, x, y,altoPantalla);
        this.imagenDanhada = imagenDanhada;
    }

    /**
     * Método que actualiza la imagen a imagenDañada y activa caida
     */
    public void granCaida(){
        imagen= imagenDanhada;
        caida=true;
    }


}

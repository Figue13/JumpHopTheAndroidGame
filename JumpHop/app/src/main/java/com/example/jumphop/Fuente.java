package com.example.jumphop;

import android.content.Context;
import android.graphics.Typeface;

public class Fuente {
    /**
     * la fuente que utilizará la aplicación
     */
    private Typeface fuente;

    /**
     * Constructor que inicializa la fuente
     * @param context el contexto de la aplicación
     */
    public Fuente(Context context){
        String fuente1="Fuentes/minya.ttf";
        this.fuente=Typeface.createFromAsset(context.getAssets(),fuente1);
    }
    public Typeface getFuente(){return fuente;}
}

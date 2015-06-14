package com.bahackaton.asistenteparaestacionar;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by alumno on 14/06/2015.
 */
public class Marcador {
    private MarkerOptions marcador;
    private double radio;
    private int vereda;
    private double latitud;
    private double longitud;


    public boolean puedeEstacionar(double lat, double lon, int ver){
        if(vereda==ver||ver==Vereda.cualquiera){
            if(lat<=latitud+radio&&lat>=latitud-radio){
                if(lon<=longitud+radio&&lon>=longitud-radio){
                    return true;
                }
            }
        }
        return false;
    }

    Marcador(MarkerOptions marcador,double radio, int vereda, double latitud, double longitud){
        this.marcador=marcador;
        this.radio=radio;
        this.vereda=vereda;
        this.latitud=latitud;
        this.longitud=longitud;
    }
}

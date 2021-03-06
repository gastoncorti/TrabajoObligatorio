/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Soporte-1
 */
public class Diccionario {

    private NodoAVL raiz;

    public Diccionario() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public boolean insertar(Ciudad ciudad, String clave) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(ciudad, clave);
            exito = true;
        } else {
            exito = insertarAux(ciudad, clave, raiz, null);
        }
        return exito;
    }

    public boolean insertarAux(Ciudad ciudad, String clave, NodoAVL actual, NodoAVL padre) {
        boolean exito = false;
        if (!actual.getClave().equals(clave)) //Clave no repetida.
        {
            if (actual.getClave().compareTo(clave) > 0)// Clave mayor alfabeticamente.
            {
                if (actual.getHijoDerecho() == null) {
                    actual.setHijoDerecho(new NodoAVL(ciudad, clave));//Inserta hijo derecho.
                    exito = true;
                } else {
                    exito = insertarAux(ciudad, clave, actual.getHijoDerecho(), actual);// Baja por la derecha.                    
                }
            } else {
                if (actual.getHijoIzquierdo() == null) {
                    actual.setHijoIzquierdo(new NodoAVL(ciudad, clave));// Inserta hijo izquierdo.
                    exito = true;
                } else {
                    exito = insertarAux(ciudad, clave, actual.getHijoIzquierdo(), actual);//Baja por la izquierda.
                }
            }

        }
        if(exito){
            actual.setAltura(altura(actual));
            //balancear(actual,padre);
        }
        

        return exito;
    }

    private int altura(NodoAVL actual) {
        return alturaAux(actual);

    }
    
    private int alturaAux(NodoAVL actual){
        int altD = 0, altI=0, alt;
        if(actual!=null){
        if(actual.getHijoIzquierdo()!=null){
            altI= 1+ alturaAux(actual.getHijoIzquierdo());
        }
        if(actual.getHijoDerecho()!=null){
            altD= 1+ alturaAux(actual.getHijoDerecho());
        }        
        alt = (altI>=altD)? altI: altD;
        
        }
alt = 0;       
        return alt;
    }

}

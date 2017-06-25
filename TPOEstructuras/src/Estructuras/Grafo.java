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
public class Grafo {

    private NodoVer inicio;

    public Grafo() {
        inicio = null;
    }

    public NodoVer ubicarVertice(String buscado) {
        NodoVer aux = this.inicio;
        while (aux != null && !aux.getElemento().equals(buscado)) {
            aux = aux.getSigVer();
        }
        return aux;
    }

    public boolean insertarVertice(String elemento) {
        boolean seInserto = false;
        NodoVer nVertice = ubicarVertice(elemento);
        if (nVertice == null) {
            this.inicio = new NodoVer(elemento, inicio);
            seInserto = true;
        }
        return seInserto;
    }

    public boolean insertarArco(String ori, String des, double etiqueta) {
        boolean seInserto = false;
        NodoVer origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVer destino = ubicarVertice(des);
            if (destino != null) {
                NodoAdy adyacente = origen.getPrimerAdy();
                if (adyacente != null) {
                    while (adyacente != null) {
                        adyacente = adyacente.getSigAdy();
                    }
                    adyacente.setSigAdy(new NodoAdy(destino, etiqueta, null));
                } else {
                    origen.setPrimerAdy(new NodoAdy(destino, etiqueta, null));
                }
                seInserto = true;
            }
        }
        return seInserto;
    }

    public boolean eliminarVertice(String elemento) {
        boolean seElimino = false;
        NodoVer anterior = inicio; //SI EL PRIMERO ES NULO LA PASAS A CAGAR CREO :O
        if (anterior.getElemento().equals(elemento)) {
            inicio = inicio.getSigVer();
            seElimino = true;
        } else {
            while (anterior.getSigVer() != null && !seElimino) {
                if (anterior.getSigVer().getElemento().equals(elemento)) {
                    anterior.setSigVer(anterior.getSigVer().getSigVer());
                    seElimino = true;
                } else {
                    anterior = anterior.getSigVer();
                }
            }
        }
        if (seElimino) {
            eliminarAdyacentesAlVertice(elemento);
        }
        return seElimino;
    }

    private boolean eliminarAdyacentesAlVertice(String elemento) {
        boolean seElimino = false;
        NodoVer vertAux = inicio;
        NodoAdy adyAux;
        while (vertAux != null) {
            adyAux = vertAux.getPrimerAdy();
            if (adyAux != null) {
                if (adyAux.getVertice().getElemento().equals(elemento)) {
                    /*
                    Suponete que el vertice es el A y tiene como primer elemennto de la lista de ady al elemento que vos queres eliminar
                    
                    Lo que creo que es lo correcto para mi es:
                    vertAux.setPrimerAdy(adyAux.getSigAdy());
                    */
                    adyAux.setSigAdy(adyAux.getSigAdy());
                    seElimino = true;
                } else {
                    while (adyAux.getSigAdy() != null && !seElimino) {
                        if (adyAux.getSigAdy().getVertice().getElemento().equals(elemento)) {
                            adyAux.setSigAdy(adyAux.getSigAdy().getSigAdy());
                            seElimino = true;
                        } else {
                            adyAux = adyAux.getSigAdy();
                        }
                    }
                }
            }
            vertAux = vertAux.getSigVer();
        }
        return seElimino;
    }

    public boolean eliminarAdyacente(String origen, String destino) {
        boolean seElimino = false;
        NodoVer auxVer = ubicarVertice(origen);
        if (auxVer != null) {
            NodoAdy auxAdy = auxVer.getPrimerAdy();
            if (auxAdy != null) {
                if (auxAdy.getVertice().getElemento().equals(destino)) {
                    auxVer.setPrimerAdy(auxAdy.getSigAdy());
                    seElimino = true;
                } else {
                    while (auxAdy.getSigAdy() != null && !seElimino) {
                        if (auxAdy.getSigAdy().getVertice().getElemento().equals(destino)) {
                            auxAdy.setSigAdy(auxAdy.getSigAdy().getSigAdy());
                            seElimino = true;
                        } else {
                            auxAdy = auxAdy.getSigAdy();
                        }

                    }
                }
            }
        }
        return seElimino;
    }
    
    public Lista listarProfundidad() {
        Lista visitados = new Lista();
        NodoVer aux = this.inicio;
        while (aux != null) {
            if (!visitados.pertenece(aux.getElemento())) {
                profundidadDesde(aux, visitados);
            }
            aux = aux.getSigVer();
        }

        return visitados;
    }

    private void profundidadDesde(NodoVer nodov, Lista visitados) {
        if (nodov != null) {
            visitados.insertar(nodov.getElemento());
            NodoAdy ady = nodov.getPrimerAdy();
            while (ady != null) {
                if (!visitados.pertenece(ady.getVertice().getElemento())) {
                    profundidadDesde(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdy();
            }
        }
    }
    
    public boolean existeCamino(String origen, String destino) {
        boolean existe = false;

        NodoVer o = ubicarVertice(origen);
        NodoVer d = ubicarVertice(destino);

        if (o != null && d != null) {
            Lista visitados = new Lista();
            existe = existeCaminoAux(o, destino, visitados);
        }

        return existe;
    }

    private boolean existeCaminoAux(NodoVer origen, String destino, Lista visitados) {
        boolean existe = false;
        if (origen != null) {
            if (origen.getElemento().equals(destino)) {
                existe = true;
            } else {
                visitados.insertar(origen.getElemento());
                NodoAdy ady = origen.getPrimerAdy();
                while (!existe && ady != null) {
                    if (!visitados.pertenece(ady.getVertice().getElemento())) {
                        existe = existeCaminoAux(ady.getVertice(), destino, visitados);
                    }
                    ady = ady.getSigAdy();
                }
            }
        }
        return existe;
    }

}

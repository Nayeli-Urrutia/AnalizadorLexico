package org.miumg;

public class TokenInfo {
    private final String lexema;
    private final String tipo;
    private final int linea;
    private final int columna;
    private final boolean error;
    private final String detalle;

    public TokenInfo(String lexema, String tipo, int linea, int columna, boolean error, String detalle) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.error = error;
        this.detalle = detalle;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isError() {
        return error;
    }

    public String getDetalle() {
        return detalle;
    }

    @Override
    public String toString() {
        return String.format(
                "Línea %d, Col %d | %-20s | %-22s | %s",
                linea, columna, lexema, tipo, detalle
        );
    }
}
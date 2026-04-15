package org.miumg;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnalizadorLexico {

    private static final Set<String> PALABRAS_RESERVADAS = Set.of("if", "else", "for", "print", "int");
    private static final Set<String> OPERADORES_ARITMETICOS = Set.of("+", "-", "*", "/");
    private static final Set<String> OPERADORES_RELACIONALES = Set.of(">=", "<=", ">", "<", "=", "<>");
    private static final Set<String> SIMBOLOS = Set.of("{", "}", "[", "]", "(", ")", ",", ";");
    private static final Set<String> OPERADORES_COMPUESTOS = Set.of(":=", ">=", "<=", "<>", "..");

    public List<TokenInfo> analizar(String codigo) {
        List<TokenInfo> resultado = new ArrayList<>();

        String[] lineas = codigo.split("\\R", -1);

        for (int i = 0; i < lineas.length; i++) {
            analizarLinea(lineas[i], i + 1, resultado);
        }

        return resultado;
    }

    private void analizarLinea(String lineaTexto, int numeroLinea, List<TokenInfo> resultado) {
        int i = 0;

        while (i < lineaTexto.length()) {
            char actual = lineaTexto.charAt(i);

            if (Character.isWhitespace(actual)) {
                i++;
                continue;
            }

            int columna = i + 1;

            // Operadores compuestos
            if (i + 1 < lineaTexto.length()) {
                String doble = lineaTexto.substring(i, i + 2);
                if (OPERADORES_COMPUESTOS.contains(doble)) {
                    resultado.add(clasificarLexema(doble, numeroLinea, columna));
                    i += 2;
                    continue;
                }
            }

            // Identificadores o palabras reservadas
            if (Character.isLetter(actual)) {
                int inicio = i;
                i++;

                while (i < lineaTexto.length() && Character.isLetterOrDigit(lineaTexto.charAt(i))) {
                    i++;
                }

                String lexema = lineaTexto.substring(inicio, i);
                resultado.add(clasificarLexema(lexema, numeroLinea, columna));
                continue;
            }

            // Números o error tipo 12abc
            if (Character.isDigit(actual)) {
                int inicio = i;
                i++;

                while (i < lineaTexto.length() && Character.isDigit(lineaTexto.charAt(i))) {
                    i++;
                }

                if (i < lineaTexto.length() && Character.isLetter(lineaTexto.charAt(i))) {
                    while (i < lineaTexto.length() && Character.isLetterOrDigit(lineaTexto.charAt(i))) {
                        i++;
                    }

                    String lexemaError = lineaTexto.substring(inicio, i);
                    resultado.add(new TokenInfo(
                            lexemaError,
                            "Error léxico",
                            numeroLinea,
                            columna,
                            true,
                            "Un identificador no puede iniciar con número."
                    ));
                    continue;
                }

                String numero = lineaTexto.substring(inicio, i);
                resultado.add(clasificarLexema(numero, numeroLinea, columna));
                continue;
            }

            // ++ y --
            if (i + 1 < lineaTexto.length()) {
                String doble = lineaTexto.substring(i, i + 2);
                if (doble.equals("++") || doble.equals("--")) {
                    resultado.add(new TokenInfo(
                            doble,
                            "Error sintáctico",
                            numeroLinea,
                            columna,
                            true,
                            "El operador " + doble + " no está definido en la gramática."
                    ));
                    i += 2;
                    continue;
                }
            }

            // Símbolos y operadores simples
            String uno = String.valueOf(actual);
            if (OPERADORES_ARITMETICOS.contains(uno) ||
                    OPERADORES_RELACIONALES.contains(uno) ||
                    SIMBOLOS.contains(uno)) {
                resultado.add(clasificarLexema(uno, numeroLinea, columna));
                i++;
                continue;
            }

            // Cualquier otro carácter es error léxico
            resultado.add(new TokenInfo(
                    uno,
                    "Error léxico",
                    numeroLinea,
                    columna,
                    true,
                    "Carácter no válido dentro del lenguaje."
            ));
            i++;
        }
    }

    private TokenInfo clasificarLexema(String lexema, int linea, int columna) {

        if (PALABRAS_RESERVADAS.contains(lexema)) {
            return new TokenInfo(lexema, "Palabra reservada", linea, columna, false, "Token válido");
        }

        if (lexema.contains("asdfg")) {
            return new TokenInfo(lexema, "Cadena con asdfg", linea, columna, false, "Token válido");
        }

        if (lexema.equals(":=")) {
            return new TokenInfo(lexema, "Operador de asignación", linea, columna, false, "Token válido");
        }

        if (OPERADORES_ARITMETICOS.contains(lexema)) {
            return new TokenInfo(lexema, "Operador aritmético", linea, columna, false, "Token válido");
        }

        if (OPERADORES_RELACIONALES.contains(lexema)) {
            return new TokenInfo(lexema, "Operador relacional", linea, columna, false, "Token válido");
        }

        if (SIMBOLOS.contains(lexema) || lexema.equals("..")) {
            return new TokenInfo(lexema, "Símbolo", linea, columna, false, "Token válido");
        }

        if (esNumero(lexema)) {
            return new TokenInfo(lexema, "Número entero", linea, columna, false, "Token válido");
        }

        if (esIdentificador(lexema)) {
            return new TokenInfo(lexema, "Identificador", linea, columna, false, "Token válido");
        }

        if (esIdentificadorLargo(lexema)) {
            return new TokenInfo(
                    lexema,
                    "Error léxico",
                    linea,
                    columna,
                    true,
                    "El identificador supera los 10 caracteres permitidos."
            );
        }

        if (esNumeroFueraRango(lexema)) {
            return new TokenInfo(
                    lexema,
                    "Error léxico",
                    linea,
                    columna,
                    true,
                    "El número está fuera del rango permitido (0 a 100)."
            );
        }

        return new TokenInfo(
                lexema,
                "Error léxico",
                linea,
                columna,
                true,
                "Lexema no reconocido por las reglas del lenguaje."
        );
    }

    private boolean esIdentificador(String token) {
        return token.matches("^[a-zA-Z][a-zA-Z0-9]{0,9}$");
    }

    private boolean esNumero(String token) {
        return token.matches("^(100|[0-9]{1,2})$");
    }

    private boolean esIdentificadorLargo(String token) {
        return token.matches("^[a-zA-Z][a-zA-Z0-9]+$") && token.length() > 10;
    }

    private boolean esNumeroFueraRango(String token) {
        if (!token.matches("^\\d+$")) {
            return false;
        }
        try {
            return Integer.parseInt(token) > 100;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
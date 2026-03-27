package org.miumg;

import java.util.*;
import java.util.regex.*;

public class AnalizadorLexico {

    static Set<String> palabrasReservadas = new HashSet<>(Arrays.asList(
            "if", "else", "for", "print", "int"
    ));

    static Set<String> operadoresAritmeticos = new HashSet<>(Arrays.asList(
            "+", "-", "*", "/"
    ));

    static Set<String> operadoresRelacionales = new HashSet<>(Arrays.asList(
            ">=", "<=", ">", "<", "=", "<>"
    ));

    static Set<String> simbolos = new HashSet<>(Arrays.asList(
            "{", "}", "[", "]", "(", ")", ",", ";", ".."
    ));

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el codigo fuente:");

        String codigo = scanner.nextLine();

        System.out.println("\n----- RESULTADO DEL ANALISIS -----\n");

        String[] tokens = codigo.split("\\s+");

        for(String token : tokens){

            if(palabrasReservadas.contains(token)){
                System.out.println("Palabra reservada -> " + token);
            }

            else if(token.contains("asdfg")){
                System.out.println("Cadena con asdfg -> " + token);
            }

            else if(operadoresAritmeticos.contains(token)){
                System.out.println("Operador aritmetico -> " + token);
            }

            else if(token.equals(":=")){
                System.out.println("Operador asignacion -> " + token);
            }

            else if(operadoresRelacionales.contains(token)){
                System.out.println("Operador relacional -> " + token);
            }

            else if(simbolos.contains(token)){
                System.out.println("Simbolo -> " + token);
            }

            else if(esNumero(token)){
                System.out.println("Numero entero -> " + token);
            }

            else if(esIdentificador(token)){
                System.out.println("Identificador -> " + token);
            }

            else if(errorLexico(token)){
                System.out.println("ERROR LEXICO -> " + token);
            }

            else if(errorSintactico(token)){
                System.out.println("ERROR SINTACTICO -> " + token);
            }

            else if(errorSemantico(token)){
                System.out.println("ERROR SEMANTICO -> " + token);
            }

            else if(errorLogico(token)){
                System.out.println("ERROR LOGICO -> " + token);
            }

            else{
                System.out.println("ERROR DESCONOCIDO -> " + token);
            }

        }
    }

    public static boolean esIdentificador(String token){

        Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{0,9}$");

        Matcher m = p.matcher(token);

        return m.matches();
    }

    public static boolean esNumero(String token){

        Pattern p = Pattern.compile("^(100|[0-9]{1,2})$");

        Matcher m = p.matcher(token);

        return m.matches();
    }

    public static boolean errorLexico(String token){

        Pattern p = Pattern.compile("^[0-9]+[a-zA-Z]+");

        Matcher m = p.matcher(token);

        return m.matches();
    }

    public static boolean errorSintactico(String token){

        return token.equals("++") || token.equals("--");
    }

    public static boolean errorSemantico(String token){

        return token.equals("tipo_error");
    }

    public static boolean errorLogico(String token){

        return token.equals("div0");
    }

}
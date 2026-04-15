package org.miumg;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder codigo = new StringBuilder();

        System.out.println("Ingrese el código fuente línea por línea.");
        System.out.println("Escriba FIN en una línea sola para terminar:\n");

        while (true) {
            String linea = scanner.nextLine();
            if ("FIN".equalsIgnoreCase(linea.trim())) {
                break;
            }
            codigo.append(linea).append("\n");
        }

        AnalizadorLexico analizador = new AnalizadorLexico();
        List<TokenInfo> tokens = analizador.analizar(codigo.toString());

        System.out.println("\n========== RESULTADO DEL ANÁLISIS ==========\n");

        for (TokenInfo token : tokens) {
            System.out.println(token);
        }
    }
}
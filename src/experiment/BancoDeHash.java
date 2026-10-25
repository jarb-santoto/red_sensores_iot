package experiment;

import model.ClaveLectura;
import model.LecturaSensor;
import repository.TablaHash;

import java.util.*;
import java.io.*;

public class BancoDeHash {

    public static void main(String[] args) {
        System.out.println("=== EXPERIMENTOS SEMANA 8: TABLAS HASH ===\n");

        experimentoUno();
        experimentoDos();
        experimentoTres();
        experimentoCuatro();
        experimentoCinco();
    }

    private static void experimentoUno() {
        System.out.println("--- Experimento 1: ArrayList vs HashSet (Búsqueda de Duplicados) ---");
        int[] tamaños = {5000, 20000, 50000};

        for (int n : tamaños) {
            List<LecturaSensor> lista = generarLecturas(n);

            // Medir ArrayList
            long inicio = System.currentTimeMillis();
            int dupArrayList = contarDuplicadosArrayList(lista);
            long fin = System.currentTimeMillis();
            long tiempoArrayList = fin - inicio;

            // Medir HashSet
            inicio = System.currentTimeMillis();
            int dupHashSet = contarDuplicadosHashSet(lista);
            fin = System.currentTimeMillis();
            long tiempoHashSet = fin - inicio;

            System.out.printf("N=%d | ArrayList: %d ms | HashSet: %d ms | Ratio: %.1fx%n",
                n, tiempoArrayList, tiempoHashSet, (double)tiempoArrayList/tiempoHashSet);
        }
        System.out.println();
    }

    private static int contarDuplicadosArrayList(List<LecturaSensor> lista) {
        int duplicados = 0;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(i).equals(lista.get(j))) {
                    duplicados++;
                    break;
                }
            }
        }
        return duplicados;
    }

    private static int contarDuplicadosHashSet(List<LecturaSensor> lista) {
        Set<LecturaSensor> set = new HashSet<>();
        int duplicados = 0;
        for (LecturaSensor l : lista) {
            if (!set.add(l)) duplicados++;
        }
        return duplicados;
    }

    private static void experimentoDos() {
        System.out.println("--- Experimento 2: Calidad de la Función Hash ---");
        int n = 20000;
        List<LecturaSensor> lecturas = generarLecturas(n);

        TablaHash<ClaveLectura, LecturaSensor> tabla = new TablaHash<>(1024);

        long inicio = System.currentTimeMillis();
        for (LecturaSensor l : lecturas) {
            tabla.poner(new ClaveLectura(l.getIdSensor(), l.getTimestamp()), l);
        }
        long fin = System.currentTimeMillis();

        System.out.println("Casillas vacias:            " + tabla.contarCasillasVacias() + " de " + tabla.numCasillas());
        System.out.println("Cadena mas larga:           " + tabla.obtenerCadenaMasLarga());
        System.out.println("Tiempo de insercion:        " + (fin - inicio) + " ms");
        System.out.println();
    }

    private static void experimentoTres() {
        System.out.println("--- Experimento 3: Factor de Carga y Redimensionamiento ---");
        // La TablaHash ya implementa redimensionamiento automático
        TablaHash<ClaveLectura, LecturaSensor> tabla = new TablaHash<>(16);
        int n = 10000;

        for (int i = 0; i < n; i++) {
            LecturaSensor l = generarUnaLectura(i);
            tabla.poner(new ClaveLectura(l.getIdSensor(), l.getTimestamp()), l);
        }

        System.out.println("Elementos insertados:       " + tabla.tamano());
        System.out.println("Casillas finales:           " + tabla.numCasillas());
        System.out.println("Cadena mas larga:           " + tabla.obtenerCadenaMasLarga());
        System.out.println();
    }

    private static void experimentoCuatro() {
        System.out.println("--- Experimento 4: Contrato equals/hashCode ---");
        // Simulando dos objetos idénticos pero diferentes instancias
        ClaveLectura a = new ClaveLectura("EST-001", "2026-01-01 10:00");
        ClaveLectura b = new ClaveLectura("EST-001", "2026-01-01 10:00");

        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.hashCode() == b.hashCode(): " + (a.hashCode() == b.hashCode()));

        Set<ClaveLectura> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println("Tamano del set (deberia ser 1): " + set.size());
        System.out.println();
    }

    private static void experimentoCinco() {
        System.out.println("--- Experimento 5: El Duplicado Escondido ---");
        // En un escenario real, leeríamos lecturas.csv
        // Aquí simulamos la detección de un duplicado en la fila 22
        List<LecturaSensor> datos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datos.add(generarUnaLectura(i));
        }
        // Insertamos el duplicado manualmente en la posición 22
        LecturaSensor original = datos.get(5);
        datos.set(22, original);

        Set<ClaveLectura> vistos = new HashSet<>();
        for (int i = 0; i < datos.size(); i++) {
            LecturaSensor l = datos.get(i);
            ClaveLectura clave = new ClaveLectura(l.getIdSensor(), l.getTimestamp());
            if (!vistos.add(clave)) {
                System.out.println("DUPLICADO en la fila " + (i + 1) + ": " + clave);
            }
        }
        System.out.println();
    }

    private static List<LecturaSensor> generarLecturas(int n) {
        List<LecturaSensor> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) lista.add(generarUnaLectura(i));
        return lista;
    }

    private static LecturaSensor generarUnaLectura(int id) {
        // Simulamos timestamps con longitud fija para probar el hash
        return new LecturaSensor("EST-" + (id % 10), "2026-03-02 07:00", 25.0 + Math.random(), 40.0 + Math.random());
    }
}

/* ============================================================
   PLATAFORMA DE MONITOREO AMBIENTAL URBANO
   BancoDeListas - VERSION COMPLETA

   Cinco experimentos. Se descomentan por fases.
   Ejecuta:  java BancoDeListas
   ============================================================ */

public class BancoDeListas {

    public static void main(String[] args) {
        experimentoUno();
        // experimentoDos();     // <- FASE 2
        // experimentoTres();    // <- FASE 3
        // experimentoCuatro(); // <- FASE 4
        // experimentoCinco();   // <- FASE 5
    }

    // ---------- EXPERIMENTO 1 ----------

    private static void experimentoUno() {
        System.out.println("=== EXP 1: EL MISMO CODIGO, DOS MECANISMOS ===");
        LecturaSensor[] datos = GeneradorDatos.generar(5);

        Repositorio[] repositorios = { new RepositorioArreglo(), new RepositorioEnlazado() };

        for (Repositorio repo : repositorios) {
            for (LecturaSensor l : datos) {
                repo.agregarAlFinal(l);
            }
            System.out.printf("%-16s tamano: %d   primera: %s%n",
                    repo.nombre(), repo.tamano(), repo.obtener(0).getTimestamp());
        }
        System.out.println();
        System.out.println("Ni una linea del ciclo de arriba sabe que hay adentro.");
        System.out.println("Eso es el contrato de la semana 2, cumplido.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 2 ----------

    private static void experimentoDos() {
        System.out.println("=== EXP 2: LA CUENTA QUE NO CUADRA ===");
        RepositorioEnlazado lista = new RepositorioEnlazado();
        for (LecturaSensor l : GeneradorDatos.generar(10)) {
            lista.agregarAlFinal(l);
        }
        System.out.println("Antes de eliminar:");
        System.out.println("   tamano() dice:        " + lista.tamano());
        System.out.println("   nodos que hay:        " + lista.contarRecorriendo());

        lista.eliminar(5);

        System.out.println("Despues de eliminar la posicion 5:");
        System.out.println("   tamano() dice:        " + lista.tamano());
        System.out.println("   nodos que hay:        " + lista.contarRecorriendo());
        System.out.println();
        System.out.println("Se pidio eliminar UNA lectura. Cuenta cuantas desaparecieron.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 3 ----------

    private static void experimentoTres() {
        System.out.println("=== EXP 3: INSERTAR AL INICIO ===");
        int[] tamanos = {10_000, 50_000, 100_000};
        System.out.printf("%10s | %26s | %26s%n", "lecturas", "ARREGLO", "LISTA ENLAZADA");
        System.out.printf("%10s | %14s %11s | %14s %11s%n", "", "movimientos", "ms", "saltos", "ms");

        for (int n : tamanos) {
            LecturaSensor[] datos = GeneradorDatos.generar(n);

            RepositorioArreglo.reiniciarContadores();
            RepositorioArreglo arreglo = new RepositorioArreglo();
            long t = System.currentTimeMillis();
            for (LecturaSensor l : datos) arreglo.agregarAlInicio(l);
            long msArreglo = System.currentTimeMillis() - t;

            RepositorioEnlazado.reiniciarSaltos();
            RepositorioEnlazado lista = new RepositorioEnlazado();
            t = System.currentTimeMillis();
            for (LecturaSensor l : datos) lista.agregarAlInicio(l);
            long msLista = System.currentTimeMillis() - t;

            System.out.printf("%,10d | %,14d %,11d | %,14d %,11d%n",
                    n, RepositorioArreglo.getMovimientos(), msArreglo,
                    RepositorioEnlazado.getSaltos(), msLista);
        }
        System.out.println();
    }

    // ---------- EXPERIMENTO 4 ----------

    private static void experimentoCuatro() {
        System.out.println("=== EXP 4: RECORRER POR POSICION ===");
        int[] tamanos = {1_000, 10_000, 50_000};
        System.out.printf("%10s | %14s %8s | %16s %8s%n",
                "lecturas", "arreglo: acc.", "ms", "lista: saltos", "ms");

        for (int n : tamanos) {
            LecturaSensor[] datos = GeneradorDatos.generar(n);

            RepositorioArreglo arreglo = new RepositorioArreglo();
            RepositorioEnlazado lista = new RepositorioEnlazado();
            for (LecturaSensor l : datos) { arreglo.agregarAlFinal(l); lista.agregarAlInicio(l); }

            long t = System.currentTimeMillis();
            for (int i = 0; i < n; i++) arreglo.obtener(i);
            long msArreglo = System.currentTimeMillis() - t;

            RepositorioEnlazado.reiniciarSaltos();
            t = System.currentTimeMillis();
            for (int i = 0; i < n; i++) lista.obtener(i);
            long msLista = System.currentTimeMillis() - t;

            System.out.printf("%,10d | %,14d %,8d | %,16d %,8d%n",
                    n, (long) n, msArreglo, RepositorioEnlazado.getSaltos(), msLista);
        }
        System.out.println();
        System.out.println("El ciclo 'for (i=0; i<n; i++) obtener(i)' se ve identico en los dos casos.");
        System.out.println("Compara la columna de saltos con la de accesos.");
        System.out.println();
    }

    // ---------- EXPERIMENTO 5 ----------

    private static void experimentoCinco() {
        System.out.println("=== EXP 5: BUSQUEDA BINARIA SOBRE UNA CADENA ===");
        int n = 50_000;
        LecturaSensor[] datos = GeneradorDatos.generar(n);
        RepositorioEnlazado lista = new RepositorioEnlazado();
        for (LecturaSensor l : datos) lista.agregarAlFinal(l);

        String objetivo = GeneradorDatos.timestampEnPosicion(n - 1);

        RepositorioEnlazado.reiniciarSaltos();
        int posLineal = lista.buscarPorTimestamp(objetivo);
        long saltosLineal = RepositorioEnlazado.getSaltos();

        RepositorioEnlazado.reiniciarSaltos();
        int posBinaria = binariaSobreLista(lista, objetivo);
        long saltosBinaria = RepositorioEnlazado.getSaltos();

        System.out.println("Buscando la ultima lectura de " + String.format("%,d", n) + ":");
        System.out.printf("   Busqueda LINEAL  -> posicion %d, saltos entre nodos: %,d%n",
                posLineal, saltosLineal);
        System.out.printf("   Busqueda BINARIA -> posicion %d, saltos entre nodos: %,d%n",
                posBinaria, saltosBinaria);
        System.out.println();
        System.out.println("La binaria hace 16 comparaciones, igual que sobre el arreglo.");
        System.out.println("Mira lo que le cuesta LLEGAR a cada una de esas 16 posiciones.");
        System.out.println();
    }

    private static int binariaSobreLista(Repositorio repo, String timestamp) {
        int inicio = 0;
        int fin = repo.tamano() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            int cmp = repo.obtener(medio).getTimestamp().compareTo(timestamp);
            if (cmp == 0) return medio;
            else if (cmp < 0) inicio = medio + 1;
            else fin = medio - 1;
        }
        return -1;
    }
}

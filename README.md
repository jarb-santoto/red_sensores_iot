# Proyecto Integrador — Plataforma de Monitoreo Ambiental Urbano

## Visión general

Este repositorio contiene la solución completa del proyecto integrado que se desarrolla a lo largo de **siete semanas** del curso de Estructuras de Datos. Cada semana añade una capa de funcionalidad, manteniendo un único punto de entrada (`IngestaSensores.java`).  

| Semana | Tema | Principales clases añadidas |
|--------|------|------------------------------|
| 1 | Ingesta de datos y validación | `IngestaSensores`, `LecturaSensor` |
| 2 | Almacenamiento en arreglo | `Repositorio`, `RepositorioArreglo`, `RepositorioLecturas` |
| 3 | Búsqueda (lineal y binaria) | `BuscadorLecturas`, `AnalizadorMatriz` |
| 4 | Algoritmos de ordenamiento | `Ordenador`, `BancoDeOrdenamiento` |
| 5 | Listas enlazadas y doblemente enlazadas | `Nodo`, `RepositorioEnlazado`, `HistorialNavegable`, `BancoDeListas` |
| 6 | Pilas, Colas y Montículos | `PilaDeAcciones`, `ColaDeIngesta`, `ColaDeAlertas`, `Alerta`, `BancoDeFlujo` |
| 7 | Java Collections Framework (JCF) | `RepositorioColeccion`, `BancoDeColecciones` |

## Arquitectura y flujo

1. **Ingesta** (`IngestaSensores.java`):
   - Lee el archivo CSV `data/lecturas_ampliadas.csv`.
   - Valida cada línea (formato y rangos físicos).
   - Inserta lecturas en el **Repositorio** configurado (por defecto `RepositorioArreglo`).
   - Actualiza el **AnalizadorMatriz** para estadísticas por hora.
2. **Almacenamiento** (`RepositorioArreglo` / `RepositorioEnlazado` / `RepositorioColeccion`):
   - Implementan la interfaz `Repositorio` que expone `agregarAlFinal`, `agregarAlInicio`, `obtener`, `eliminar`, `tamano` y `nombre`.
   - `RepositorioArreglo` mantiene un arreglo dinámico con contadores de movimientos.
   - `RepositorioEnlazado` es una lista enlazada simple con optimizaciones (uso del puntero `ultimo`).
   - `RepositorioColeccion` delega la funcionalidad al JCF (`ArrayList` o `LinkedList`).
3. **Búsqueda** (`BuscadorLecturas`):
   - Búsqueda lineal y binaria por *timestamp*.
   - Búsqueda binaria por *PM2.5* (requiere datos ordenados por ese criterio).
4. **Ordenamiento** (`Ordenador`):
   - Implementaciones de Bubble‑Sort (con corte temprano), Selection‑Sort, Insertion‑Sort, Merge‑Sort, Quick‑Sort (pivote aleatorio) y Heap‑Sort.
   - Contadores de comparaciones e intercambios para medir coste.
5. **Listas enlazadas** (`RepositorioEnlazado`, `HistorialNavegable`):
   - Inserción/ eliminación en O(1) o O(n) según caso.
   - `HistorialNavegable` permite navegación hacia adelante y atrás con un cursor, usado por la UI de control.
6. **Control de Flujo (Semana 6)**:
   - **Pila de Acciones**: Gestiona el historial de operaciones del operador para permitir el "Deshacer" (LIFO).
   - **Cola de Ingesta**: Buffer circular que recibe lecturas de la red para procesarlas secuencialmente sin perder datos (FIFO).
   - **Cola de Alertas**: Gestión de prioridad mediante un **Montículo (Heap)**, asegurando que las alertas críticas se atiendan primero, con desempate por orden de llegada.
7. **Colecciones Estándar (Semana 7)**:
   - Implementación de repositorios usando `List` del JCF.
   - Análisis de rendimiento entre `ArrayList` y `LinkedList`.
   - Uso de `Comparator` para ordenamientos personalizados y estables.

## Cómo compilar y ejecutar

```bash
# Desde la raíz del proyecto
javac -d out src/**/*.java test/*.java

# Ejecutar la aplicación principal (ingesta completa)
java -cp out ingest.IngestaSensores
```

> **Requisitos**: el archivo `data/lecturas_ampliadas.csv` debe estar presente.

## Ejecutar experimentos por semana

- **Semana 3**: `java experiment.BancoDePruebas` (ejecuta los experimentos de búsqueda).
- **Semana 4**: `java experiment.BancoDeOrdenamiento` (prueba los algoritmos de ordenamiento).
- **Semana 5**: `java experiment.BancoDeListas` (compara arreglo vs lista enlazada y prueba la lista doble).
- **Semana 6**: `java experiment.BancoDeFlujo` (prueba pila, cola circular y cola de prioridad).
- **Semana 7**: `java experiment.BancoDeColecciones` (compara implementaciones del JCF).

## Tests

El proyecto incluye un conjunto completo de pruebas unitarias bajo `test/` que cubren:
- Validación y acceso de `LecturaSensor`.
- Todas las implementaciones de `Repositorio`.
- Algoritmos de ordenamiento y sus contadores.
- Búsqueda lineal y binaria.
- Navegación en la lista doble (`HistorialNavegable`).
- Operaciones de Pila, Cola Circular y Montículo.
- Delegación a colecciones estándar del JCF.

Ejecutar todas las pruebas con JUnit:
```bash
java -jar lib/junit-platform-console-standalone-1.10.2.jar \
    --class-path out \
    --scan-class-path
```

## Decisiones de diseño (ver `docs/decisiones.md`)

- Uso de la interfaz `Repositorio` para aislar la lógica de la aplicación del mecanismo de almacenamiento.
- Implementación de **pivote aleatorio** en Quick‑Sort para evitar `StackOverflowError` con datos ya ordenados.
- Añadido de un puntero `ultimo` en la lista enlazada para `agregarAlFinal` O(1).
- Introducción de la lista doble (`HistorialNavegable`) para navegación bidireccional en la UI.
- Uso de **aritmética de índices** para el Montículo, evitando el uso de nodos y referencias para optimizar el rendimiento.
- Criterio de desempate en la Cola de Prioridad basado en el **orden de llegada** para garantizar equidad en alertas de misma severidad.
- Elección de `ArrayList` para el `RepositorioColeccion` basándose en la frecuencia de inserciones al final y recorridos completos.

---

**Autor**: José Alvaro Rodríguez Botero (Claude Fable 5) 
**Fecha**: Oct 2026

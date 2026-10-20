# Proyecto Integrador — Plataforma de Monitoreo Ambiental Urbano

## Visión general

Este repositorio contiene la solución completa del proyecto integrado que se desarrolla a lo largo de **cinco semanas** del curso de Estructuras de Datos. Cada semana añade una capa de funcionalidad, manteniendo un único punto de entrada (`IngestaSensores.java`).  

| Semana | Tema | Principales clases añadidas |
|--------|------|------------------------------|
| 1 | Ingesta de datos y validación | `IngestaSensores`, `LecturaSensor` |
| 2 | Almacenamiento en arreglo | `Repositorio`, `RepositorioArreglo`, `RepositorioLecturas` |
| 3 | Búsqueda (lineal y binaria) | `BuscadorLecturas`, `AnalizadorMatriz` |
| 4 | Algoritmos de ordenamiento | `Ordenador`, `BancoDeOrdenamiento` |
| 5 | Listas enlazadas y doblemente enlazadas | `Nodo`, `RepositorioEnlazado`, `HistorialNavegable`, `BancoDeListas` |

## Arquitectura y flujo

1. **Ingesta** (`IngestaSensores.java`):
   - Lee el archivo CSV `data/lecturas_ampliadas.csv`.
   - Valida cada línea (formato y rangos físicos).
   - Inserta lecturas en el **Repositorio** configurado (por defecto `RepositorioArreglo`).
   - Actualiza el **AnalizadorMatriz** para estadísticas por hora.
2. **Almacenamiento** (`RepositorioArreglo` / `RepositorioEnlazado`):
   - Implementan la interfaz `Repositorio` que expone `agregarAlFinal`, `agregarAlInicio`, `obtener`, `eliminar`, `tamano` y `nombre`.
   - `RepositorioArreglo` mantiene un arreglo dinámico con contadores de movimientos.
   - `RepositorioEnlazado` es una lista enlazada simple con optimizaciones (uso del puntero `ultimo`).
3. **Búsqueda** (`BuscadorLecturas`):
   - Búsqueda lineal y binaria por *timestamp*.
   - Búsqueda binaria por *PM2.5* (requiere datos ordenados por ese criterio).
4. **Ordenamiento** (`Ordenador`):
   - Implementaciones de Bubble‑Sort (con corte temprano), Selection‑Sort, Insertion‑Sort, Merge‑Sort, Quick‑Sort (pivote aleatorio) y Heap‑Sort.
   - Contadores de comparaciones e intercambios para medir coste.
5. **Listas enlazadas** (`RepositorioEnlazado`, `HistorialNavegable`):
   - Inserción/ eliminación en O(1) o O(n) según caso.
   - `HistorialNavegable` permite navegación hacia adelante y atrás con un cursor, usado por la UI de control.

## Cómo compilar y ejecutar

```bash
# Desde la raíz del proyecto
javac -d out src/*.java test/*.java

# Ejecutar la aplicación principal (ingesta completa)
java -cp out IngestaSensores
```

> **Requisitos**: el archivo `data/lecturas_ampliadas.csv` debe estar presente.

## Ejecutar experimentos por semana

- **Semana 3**: `java BancoDePruebas` (ejecuta los experimentos de búsqueda).
- **Semana 4**: `java BancoDeOrdenamiento` (prueba los algoritmos de ordenamiento).
- **Semana 5**: `java BancoDeListas` (compara arreglo vs lista enlazada y prueba la lista doble).

## Tests

El proyecto incluye un conjunto completo de pruebas unitarias bajo `test/` que cubren:
- Validación y acceso de `LecturaSensor`.
- Todas las implementaciones de `Repositorio`.
- Algoritmos de ordenamiento y sus contadores.
- Búsqueda lineal y binaria.
- Navegación en la lista doble (`HistorialNavegable`).

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

---

**Autor**: José Alvaro Rodríguez Botero (Claude Fable 5) 
**Fecha**: Oct 2026


## Plataforma de Monitoreo Ambiental Urbano

Esta versión integra las capacidades desarrolladas durante las semanas anteriores y agrega la búsqueda lineal y binaria.

### Regla arquitectónica

Existe **un único `main` para todo el proyecto**:

```text
IngestaSensores.java
```

`BancoDePruebas.java` no contiene `main`; sus métodos son invocados desde `IngestaSensores`.

## Archivos

```text
.
├── IngestaSensores.java
├── LecturaSensor.java
├── RepositorioLecturas.java
├── AnalizadorMatriz.java
├── BuscadorLecturas.java
├── BancoDePruebas.java
├── GeneradorDatos.java
├── README.md
├── docs/
│   └── decisiones.md
└── data/
    └── lecturas_ampliadas.csv   ← requerido para ejecutar la ingesta
```

## Compilación

Desde la raíz del proyecto:

```bash
javac *.java
```

## Ejecución

```bash
java IngestaSensores
```

El archivo:

```text
data/lecturas_ampliadas.csv
```

debe existir para ejecutar la aplicación completa.

## Experimentos de Semana 3

El único `main` ejecuta:

1. Búsqueda lineal en diferentes tamaños.
2. Comparación de `String`.
3. Búsqueda lineal vs. binaria por timestamp.
4. Búsqueda binaria por PM2.5 y análisis de su precondición.

## Idea central

La aplicación no se reinicia conceptualmente cada semana.

Cada semana agrega una nueva capacidad al mismo proyecto integrador.

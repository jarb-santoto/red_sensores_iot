# Proyecto Integrador — Semana 3

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

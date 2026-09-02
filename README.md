# Proyecto Integrador Red de Sensores IoT
>Proyecto base del curso de estructura de datos.

## Modulo de ingesta

`src/IngestaSensores.java` lee el archivo `data/lecturas.csv` y muestra:

- Cada lectura con su estacion, fecha, temperatura, humedad y PM2.5.
- La cantidad de registros procesados.
- El promedio de temperatura, humedad y PM2.5.
- La estacion con el valor mas alto de PM2.5.

### Conceptos principales

- `BufferedReader` lee el archivo una linea a la vez.
- `split(",")` separa las columnas del CSV.
- `Double.parseDouble` convierte texto numerico a `double`.
- Los acumuladores suman los valores para calcular promedios.
- La condicion `if` compara cada PM2.5 con el maximo encontrado.

### Ejecucion

Desde la carpeta `src`, con un JDK instalado:

```text
javac IngestaSensores.java
java IngestaSensores
```

El programa espera encontrar `lecturas.csv` en la carpeta desde la que se
ejecuta. Por ejemplo, copia el CSV a `src` o ejecuta el programa desde `data`
ajustando la ruta del archivo en el codigo.

> Nota: el CSV de ejemplo contiene valores vacios y textos como `ERR`. El
> programa actual intenta convertir todos los valores de medicion a numero,
> por lo que esas filas pueden producir un error durante la ejecucion.

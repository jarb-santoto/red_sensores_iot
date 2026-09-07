# Guia Git - Puesta en marcha Semana 3 (pero repo creado desde S1)

## Comandos minimos (Documento Marco 10.8)

```bash
# 1. Crear repo en GitHub privado: plataforma-monitoreo-ambiental
# 2. Clonar
git clone <url>
cd plataforma-monitoreo-ambiental

# 3. Copiar esta estructura dentro

# 4. Primer estado
git status  # SIEMPRE antes de todo
git add .
git commit -m "S01: ingesta robusta con validacion y LecturaSensor reusable"
git log --oneline

# 5. Dar acceso lectura al docente en GitHub > Settings > Collaborators

# 6. Subir
git push -u origin main

# Flujo semanal
git status
git pull
git add src/modelo/LecturaSensor.java
git commit -m "S01: agrega validacion rangos fisicos"
git push
```

## Tags de entrega (requisito evaluacion)

```bash
git tag -a hito-S01 -m "Entrega S01: modularidad y robustez"
git push origin hito-S01
```

## Que NO subir
- *.class, bin/, out/, .idea/, descartes.csv (generado)
- Si subiste .class por accidente: git checkout -- <archivo> o git rm --cached
```

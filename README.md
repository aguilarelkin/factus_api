# Factus

Factus es una aplicación Android desarrollada en Kotlin que permite la gestión de facturación, clientes, productos y tributos. Utiliza Jetpack Compose para la interfaz de usuario y Dagger Hilt para la inyección de dependencias.

## Características principales
- Gestión de facturas y clientes
- Visualización de productos y periodos de facturación
- Autenticación y manejo de tokens
- Persistencia de datos con DataStore
- Consumo de APIs REST usando Retrofit y OkHttp

## Estructura del proyecto
- `app/` — Código fuente principal de la aplicación
  - `data/` — Implementaciones de repositorios y servicios de red
  - `domain/` — Modelos de dominio y repositorios
  - `ui/` — Pantallas y navegación de la interfaz de usuario
  - `res/` — Recursos gráficos, fuentes y valores

## Requisitos
- Android Studio Flamingo o superior
- JDK 8+
- SDK de Android 26+

## Instalación
1. Clona este repositorio:
   ```bash
   git clone <url-del-repositorio>
   ```
2. Abre el proyecto en Android Studio.
3. Sincroniza los gradle scripts y ejecuta la app en un emulador o dispositivo físico.

## Compilación
Desde la terminal, en la raíz del proyecto:
```bash
./gradlew assembleDebug
```

## Pruebas
Para ejecutar los tests unitarios:
```bash
./gradlew test
```

## Licencia
Este proyecto está bajo la licencia MIT.

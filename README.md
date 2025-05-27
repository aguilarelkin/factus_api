# 📲 Factus

**Factus** es una aplicación Android desarrollada en **Kotlin** que permite la gestión eficiente de facturación electrónica, clientes, productos y tributos. Diseñada con **Jetpack Compose** para una experiencia UI moderna, y potenciada con **Dagger Hilt**, **Retrofit** y **DataStore** para un rendimiento óptimo, modularidad y seguridad.

> ¡Facturación más simple, desde tu bolsillo!

---

## ✨ Características principales

- 🧾 **Gestión de Facturas y Clientes**
- 📦 **Visualización y control de productos**
- 📆 **Control de periodos de facturación**
- 🔐 **Autenticación segura con manejo de tokens**
- 💾 **Persistencia local con DataStore Preferences**
- 🌐 **Consumo eficiente de APIs REST con Retrofit y OkHttp**
- 💉 **Inyección de dependencias con Dagger Hilt**
- 🚀 **Arquitectura MVVM moderna y escalable**

---

## 🧱 Estructura del Proyecto
```text
📦 app/
├── 📁 data/        → Repositorios, modelos DTO, servicios de red
├── 📁 domain/      → Modelos de dominio, interfaces de repositorio, casos de uso
├── 📁 ui/          → Pantallas, componentes, navegación
├── 📁 di/          → Módulos de Hilt para inyección de dependencias
├── 📁 utils/       → Utilidades y helpers comunes
├── 📁 res/         → Recursos: strings, estilos, temas, íconos
└── 📝 AndroidManifest.xml
```

## 🔧 Requisitos
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

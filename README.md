# Sistema de Gestión de Torneo de Fútbol (MVC)

Aplicación desarrollada en Java bajo el patrón arquitectónico **Modelo-Vista-Controlador (MVC)** para la gestión y sorteo de eliminatorias de una liga profesional de fútbol, implementando recursividad, excepciones personalizadas y persistencia de datos mediante serialización.

---

## ⚙️ Características Principales

1. **Arquitectura MVC**: Separación estricta entre la interfaz de usuario (`vista`), la lógica de negocio y entidades (`modelo`), y el mediador de acciones (`controlador`).
2. **Algoritmo Recursivo**: Sorteo automatizado de emparejamientos por fases garantizando que no existan enfrentamientos duplicados ni equipos compitiendo contra sí mismos.
3. **Manejo de Excepciones Personalizadas**:
   - `NumeroEquiposInvalidoException`: Valida que el número de equipos participantes sea una potencia de 2 (2, 4, 8, 16).
   - `SorteoInvalidoException`: Controla fallas lógicas durante la ejecución recursiva.
4. **Persistencia de Datos**: Implementación de `Serializable` y uso de flujos binarios para guardar (`torneo.ser`) y cargar el estado completo del torneo.

---

##  Guía de Ejecución

1. Importar el proyecto en Eclipse o VS Code.
2. Mantener intactos los archivos `.classpath` y `.project`.
3. Ejecutar la clase principal ubicada en `vista/Main.java`.
4. Seguir las opciones del menú interactivo en consola para ingresar equipos, realizar el sorteo recursivo o guardar/cargar el estado.

---

## Diagrama de Clases (UML)

<p align="center">
  <img src="diagrama.png" alt="Diagrama de Clases POO" width="700">
</p>

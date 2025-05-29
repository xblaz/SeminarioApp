## Getting Started

# 📂 Estructura del Proyecto

| Carpeta                   | Rol técnico           | Justificación académica                            |
| ------------------------- | --------------------- | -------------------------------------------------- |
| `model/`                  | Entidades del dominio | Separa la lógica de datos de la vista y control    |
| `repository/`             | DAO / acceso a datos  | Cumple el principio de única responsabilidad (SRP) |
| `service/`                | Lógica de negocio     | Centraliza las reglas del sistema                  |
| `controller/`             | Controladores JavaFX  | Separa UI de la lógica del dominio (MVC)           |
| `view/`                   | Gestión de navegación | Facilita el cambio de vistas de forma desacoplada  |
| `util/`                   | Clases auxiliares     | Reutilización sin contaminar otras capas           |
| `config/`                 | Configuración general | Centraliza propiedades de conexión o constantes    |
| `resources/fxml/`, `css/` | Vistas y estilos      | Aplica principios de separación de preocupaciones  |

Este documento describe la estructura de carpetas y archivos de la aplicación.

## Dependencias

- Java 23
- JavaFX 24 
- MySQL 

MySQL 9.2.0
https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar

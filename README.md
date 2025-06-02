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
- MySQL 9
- FontAwesomeFX 4.7.0 

MySQL 9.2.0
- https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar

FontAwesomeFX
- https://repo1.maven.org/maven2/de/jensd/fontawesomefx-fontawesome/4.7.0-9.1.2/fontawesomefx-fontawesome-4.7.0-9.1.2.jar
- https://repo1.maven.org/maven2/de/jensd/fontawesomefx-commons/9.1.2/fontawesomefx-commons-9.1.2.jar
- https://bbuseruploads.s3.amazonaws.com/Jerady/fontawesomefx/downloads/fontawesomefx-8.2.jar?response-content-disposition=attachment%3B%20filename%3D%22fontawesomefx-8.2.jar%22&AWSAccessKeyId=ASIA6KOSE3BNPVWLA4YZ&Signature=lusPKxcS2VEwIm1tuCB1IOsh%2FRI%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEOz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJGMEQCIEFpRE3qAcfbSFTx%2BdGyWU8gLo7vvk4uFPrSjGjX%2FCN0AiBhPd5F%2BghKj%2BpjHeEx%2B5Wr3dB%2B%2Fya1svdnI6xp1BtiMCqwAgi1%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F8BEAAaDDk4NDUyNTEwMTE0NiIMpe3ctNiZi%2FLZlClHKoQC8DOovi3sMCocsNP3jdgHbFRJjiUH0jM%2FY7zizXTuV%2Fu9B7UQLZcA%2Fe6e22DjKO%2B%2BLVEk1a%2F%2BOcglL5bC1p%2Fxl3hIXLdMfKow0w5Sxa8Mb2ROoLj0Z9qW%2FHoQHxBOIeqt7QLO6pN%2FrBen8HGORi5SMhFKiLF551p7a2n5haALOoi1ibpdtzqdgk6CXTkh8VpNt5CpYKI2OZXwm%2B%2FGUw0ZBO5T0xDgTkCxpB40zjSzAmnyit0BCEgcy5g%2FvAzK1XwzmDn0tiwOwxfMSlRMeqrZ4aMT0RYoD%2Fh0WRlD5Dqt1XQ1WDp88guJV0iuG%2BCMWvq5fXns1XoUjPND4ILPMntcYS48Spow1frpwQY6ngGX5ycL1izNQmKB5PFETbZaCXzX8lYnXU%2BPkQTI3S1wixLHHP%2Fgf2FxYFWt9GIxUo0WRpWhVLwpxrjS0YqdXZ6gmDYkMBcWEEUGu3G%2F2k18Q0v7jSZ9pEvs%2BdDGispYWzzBiepBboivMtjp%2FB8LhDlnzqHx6OLEzCv0uYf1C%2Bp6Z0R%2Fi5tXJEffZxdoV8k04flDy9KL9iX1z4NfjfjHGA%3D%3D&Expires=1748665437
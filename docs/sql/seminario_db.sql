CREATE DATABASE seminario_db;
CREATE USER 'seminario'@'%' IDENTIFIED BY 'password123';
GRANT SELECT, INSERT, UPDATE, DELETE ON seminario_db.* TO 'seminario'@'%';
FLUSH PRIVILEGES;

CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_alta` date DEFAULT NULL,
  `fecha_baja` date DEFAULT NULL,
  `clave` varchar(150) DEFAULT NULL,
  `estado` enum('Activo','Desactivado') DEFAULT 'Desactivado',
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `permisos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) DEFAULT NULL,
  `codigo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `rol_permisos` (
  `rol_id` int NOT NULL,
  `permiso_id` int DEFAULT NULL,
  UNIQUE KEY `idx_rol_permisos_rol_id_permiso_id` (`rol_id`, `permiso_id`) USING BTREE,
  KEY `idx_rol_permisos_permiso_id` (`permiso_id`),
  CONSTRAINT `fk_rol_permisos_rol_id__rol` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`),
  CONSTRAINT `fk_rol_permisos_permiso_id__permisos` FOREIGN KEY (`permiso_id`) REFERENCES `permisos` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `usuarios_rol` (
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  UNIQUE KEY `idx_usuarios_rol_usuario_id_rol_id` (`usuario_id`, `rol_id`) USING BTREE,
  KEY `idx_usuarios_rol_rol_id` (`rol_id`),
  CONSTRAINT `fk_usuarios_rol_usuario_id__usuarios` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_usuarios_rol_rol_id__rol` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB;


INSERT INTO seminario_db.usuarios (fecha_alta,  fecha_baja, clave,  estado, nombre)
    VALUES('2025-06-01', NULL, 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ACTIVO', 'administrador');
INSERT INTO seminario_db.usuarios (fecha_alta,  fecha_baja, clave,  estado, nombre)
    VALUES('2025-06-01', NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','ACTIVO','operador');
INSERT INTO seminario_db.usuarios (fecha_alta,  fecha_baja, clave,  estado, nombre)
    VALUES('2025-06-01', NULL, 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 'ACTIVO','super');

-- Roles
INSERT INTO seminario_db.rol (id,descripcion) VALUES(1,'SUPER');
INSERT INTO seminario_db.rol (id,descripcion) VALUES(2,'ADMINISTRADOR');
INSERT INTO seminario_db.rol (id,descripcion) VALUES(3,'OPERADOR');
INSERT INTO seminario_db.rol (id,descripcion) VALUES(4,'TESORERIA');

-- Permisos
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(1, 'Permiter crear cliente', 'crear_cliente');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(2, 'Permiter crear cliente', 'modificar_cliente');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(3, 'Permitir eliminar cliente', 'eliminar_cliente');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(4, 'Permitir crear préstamo', 'crear_prestamo');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(5, 'Permitir aprobar préstamo', 'aprobar_prestamo');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(6, 'Permitir cancelar préstamo', 'cancelar_prestamo');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(7, 'Permitir registrar usuario', 'crear_usuario');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(8, 'Permitir modificar usuario', 'modificar_usuario');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(9, 'Permitir crear rol', 'crear_rol');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(10, 'Permitir modificar rol', 'modificar_rol');
INSERT INTO seminario_db.permisos (id, descripcion, codigo)
    VALUES(11, 'Permitir registrar cobro', 'registrar_cobro');

-- Relación Rol -> Permiso 1: muchos
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 7);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 8);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 9);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 10);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 5);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(2, 6);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(3, 1);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(3, 2);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(3, 3);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(3, 4);
INSERT INTO seminario_db.rol_permisos (rol_id, permiso_id) VALUES(4, 11);

-- Relación Usuario -> Rol 1: muchos
INSERT INTO seminario_db.usuarios_rol (usuario_id, rol_id) VALUES(2, 3);
INSERT INTO seminario_db.usuarios_rol (usuario_id, rol_id) VALUES(1, 2);

INSERT INTO seminario_db.tipo_domicilio (id, descripcion, codigo) VALUES(1, 'Particular', 1);
INSERT INTO seminario_db.tipo_domicilio (id, descripcion, codigo) VALUES(2, 'Laboral', 2);
INSERT INTO seminario_db.tipo_domicilio (id, descripcion, codigo) VALUES(3, 'Otro', 3);
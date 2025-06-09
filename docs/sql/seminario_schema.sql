CREATE DATABASE seminario_db;

CREATE USER 'seminario'@'%' IDENTIFIED BY 'password123';
GRANT SELECT, INSERT, UPDATE, DELETE ON seminario_db.* TO 'seminario'@'%';
FLUSH PRIVILEGES;

CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_alta` date DEFAULT NULL,
  `fecha_baja` date DEFAULT NULL,
  `clave` varchar(150) DEFAULT NULL,
  `estado` enum('Activo','Desactivado','Eliminado') 
CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Desactivado',
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuarios_nombre_IDX` (`nombre`) USING BTREE
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


CREATE TABLE `tipo_domicilio` (
  `id` int NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `codigo` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB ;



CREATE TABLE `domicilios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `piso` varchar(10) DEFAULT NULL,
  `codigo_postal` varchar(10) DEFAULT NULL,
  `localidad` varchar(100) DEFAULT NULL,
  `provincia` varchar(100) DEFAULT NULL,
  `persona_id` int NOT NULL,
  `tipo_domicilio_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_domicilios_personas1_idx` (`persona_id`),
  KEY `fk_domicilios_tipodomicilio_idx` (`tipo_domicilio_id`),
  CONSTRAINT `fk_domicilios_personas1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `fk_domicilios_tipodomicilio` FOREIGN KEY (`tipo_domicilio_id`) REFERENCES `tipo_domicilio` (`id`)
) ENGINE=InnoDB;

-- esquema_financiacion definition

CREATE TABLE `esquema_financiacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(150) DEFAULT NULL,
  `tasa_interes_anual` decimal(5,2) DEFAULT NULL,
  `tipo` enum('FRANCES','ALEMAN','AMERICANO') DEFAULT NULL,
  `cantidad_cuotas` int DEFAULT NULL,
  `requiere_garante` tinyint DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


-- personas definition

CREATE TABLE `personas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('FISICA','JURIDICA') NOT NULL,
  `fecha_registro` date DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;


-- personas_fisicas definition

CREATE TABLE `personas_fisicas` (
  `persona_id` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `dni` int DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` char(1) DEFAULT NULL,
  PRIMARY KEY (`persona_id`),
  CONSTRAINT `fk_personas_fisicas_1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB;


-- personas_juridicas definition

CREATE TABLE `personas_juridicas` (
  `persona_id` int NOT NULL,
  `razon_social` varchar(100) DEFAULT NULL,
  `cuit` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`persona_id`),
  CONSTRAINT `fk_personas_juridicas_1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB;


-- telefonos definition

CREATE TABLE `telefonos` (
  `id` int NOT NULL,
  `numero` int DEFAULT NULL,
  `cod_area` int DEFAULT NULL,
  `tipo` enum('PARTICULAR','LABORAL') NOT NULL,
  `persona_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_telefonos_1_idx` (`persona_id`),
  CONSTRAINT `fk_telefonos_1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB;


-- clientes definition

CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `persona_id` int NOT NULL,
  `nro_cliente` int DEFAULT NULL,
  `fecha_alta` date DEFAULT NULL,
  `operador` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_clientes_1_idx` (`persona_id`),
  KEY `fk_clientes_2_idx` (`operador`),
  CONSTRAINT `fk_clientes_1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `fk_clientes_2` FOREIGN KEY (`operador`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB;


-- cuentas_bancarias definition

CREATE TABLE `cuentas_bancarias` (
  `id` int NOT NULL,
  `banco` varchar(100) DEFAULT NULL,
  `nro_cuenta` varchar(100) DEFAULT NULL,
  `nro_cbu` varchar(22) DEFAULT NULL,
  `cliente_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cuentas_bancarias_clientes1_idx` (`cliente_id`),
  CONSTRAINT `fk_cuentas_bancarias_clientes1` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB;


-- cuotas definition

CREATE TABLE `cuotas` (
  `id` int NOT NULL,
  `prestamo_id` int NOT NULL,
  `numero` int DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `interes` decimal(10,2) DEFAULT NULL,
  `fecha_pago` date DEFAULT NULL,
  `capital` decimal(10,2) DEFAULT NULL,
  `saldo` decimal(10,2) DEFAULT NULL,
  `estado` enum('PENDIENTE','PAGADA','VENCIDA') DEFAULT 'PENDIENTE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_prestamo_cuota` (`prestamo_id`,`numero`),
  KEY `fk_cuotas_prestamos1_idx` (`prestamo_id`),
  CONSTRAINT `fk_cuotas_prestamos1` FOREIGN KEY (`prestamo_id`) REFERENCES `prestamos` (`id`)
) ENGINE=InnoDB;


-- domicilios definition

CREATE TABLE `domicilios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `calle` varchar(100) DEFAULT NULL,
  `numero` varchar(20) DEFAULT NULL,
  `piso` varchar(10) DEFAULT NULL,
  `codigo_postal` varchar(10) DEFAULT NULL,
  `localidad` varchar(100) DEFAULT NULL,
  `provincia` varchar(100) DEFAULT NULL,
  `persona_id` int NOT NULL,
  `tipo_domicilio_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_domicilios_personas1_idx` (`persona_id`),
  KEY `fk_domicilios_tipodomicilio_idx` (`tipo_domicilio_id`),
  CONSTRAINT `fk_domicilios_personas1` FOREIGN KEY (`persona_id`) REFERENCES `personas` (`id`),
  CONSTRAINT `fk_domicilios_tipodomicilio` FOREIGN KEY (`tipo_domicilio_id`) REFERENCES `tipo_domicilio` (`id`)
) ENGINE=InnoDB;


-- garantes definition

CREATE TABLE `garantes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prestamo_id` int DEFAULT NULL,
  `persona_fisica_id` int DEFAULT NULL,
  `relacion_con_deudor` varchar(150) DEFAULT NULL,
  `porcentaje_responsabilidad` decimal(5,2) DEFAULT NULL,
  `fecha_firma_garantia` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_garantes_1_idx` (`prestamo_id`),
  KEY `fk_garantes_2_idx` (`persona_fisica_id`),
  CONSTRAINT `fk_garantes_1` FOREIGN KEY (`prestamo_id`) REFERENCES `prestamos` (`id`),
  CONSTRAINT `fk_garantes_2` FOREIGN KEY (`persona_fisica_id`) REFERENCES `personas_fisicas` (`persona_id`)
) ENGINE=InnoDB;


-- pagos definition

CREATE TABLE `pagos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cuota_id` int NOT NULL,
  `monto_pagado` decimal(10,2) DEFAULT NULL,
  `fecha_pago` date DEFAULT NULL,
  `recargos` decimal(10,2) DEFAULT NULL,
  `estado` enum('PENDIENTE','COMPLETADO','RECHAZADO') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pagos_cuotas1_idx` (`cuota_id`),
  CONSTRAINT `fk_pagos_cuotas1` FOREIGN KEY (`cuota_id`) REFERENCES `cuotas` (`id`)
) ENGINE=InnoDB;

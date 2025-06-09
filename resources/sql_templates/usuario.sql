-- login
SELECT 1 FROM usuarios WHERE nombre = ? AND clave = ?;

-- find_by_id
SELECT
	u.id,
	u.fecha_alta,
	u.fecha_baja,
	u.estado,
	u.nombre,
	r.id as rol_id,
	r.descripcion as rol_descripcion,
	p.id as permiso_id,
	p.codigo as permiso_codigo
FROM usuarios u
JOIN usuarios_rol ur ON ur.usuario_id = u.id
JOIN rol r ON r.id = ur.rol_id
JOIN rol_permisos rp ON r.id = rp.rol_id
JOIN permisos p ON rp.permiso_id = p.id
WHERE
	u.id = ?

-- find_all
SELECT
	u.id,
	u.fecha_alta,
	u.fecha_baja,
	u.estado,
	u.nombre,
	r.id as rol_id,
	r.descripcion as rol_descripcion,
	p.id as permiso_id,
	p.codigo as permiso_codigo
FROM usuarios u
LEFT JOIN usuarios_rol ur ON ur.usuario_id = u.id
LEFT JOIN rol r ON r.id = ur.rol_id
LEFT JOIN rol_permisos rp ON r.id = rp.rol_id
LEFT JOIN permisos p ON rp.permiso_id = p.id

-- create_usuario
INSERT INTO usuarios (fecha_alta, clave, estado, nombre) VALUES (?, ? , ?, ?);

-- update_usuario
UPDATE usuarios SET estado=? WHERE id=?;

-- change_password
UPDATE usuarios SET clave=? WHERE id=?;

-- add_rol_usuario
INSERT INTO usuarios_rol (usuario_id, rol_id) VALUES(?, ?);

-- clear_user_roles
DELETE FROM usuarios_rol WHERE usuario_id=?;

-- delete
UPDATE usuarios SET estado = 'Eliminado' , fecha_baja = NOW() WHERE id=?;


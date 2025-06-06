-- find_by_id
SELECT
	u.id,
	u.fecha_alta,
	u.fecha_baja,
	u.estado,
	u.nombre,
	r.id as rol_id,
	r.descripcion as rol_nombre,
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
	r.descripcion as rol_nombre,
	p.id as permiso_id,
	p.codigo as permiso_codigo
FROM usuarios u
LEFT JOIN usuarios_rol ur ON ur.usuario_id = u.id
LEFT JOIN rol r ON r.id = ur.rol_id
LEFT JOIN rol_permisos rp ON r.id = rp.rol_id
LEFT JOIN permisos p ON rp.permiso_id = p.id

-- save_insertar
INSERT INTO usuarios (fecha_alta, clave, estado, nombre) VALUES (?, ? , ?, ?);

-- save_update
UPDATE usuarios SET
    fecha_baja=?    
    estado=? WHERE id=?;

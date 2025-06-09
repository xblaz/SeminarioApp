-- roles_por_usuario
SELECT
    u.id AS usuario_id,
    u.nombre,
    u.clave,
    r.id as rol_id,
    r.descripcion as rol_descripcion,
    p.id as permiso_id,
    p.codigo as permiso_codigo
FROM usuarios u
JOIN usuarios_rol ur ON ur.usuario_id = u.id
JOIN rol r ON r.id = ur.rol_id
JOIN rol_permisos rp ON r.id = rp.rol_id
JOIN permisos p ON rp.permiso_id = p.id
WHERE u.nombre = ?;

-- find_all
SELECT id, descripcion FROM rol;

-- find_by_id
SELECT id, descripcion FROM rol WHERE id = ?;

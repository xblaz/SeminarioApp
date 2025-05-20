SELECT 
    u.id AS usuario_id, 
    u.nombre, 
    u.username, 
    u.password_hash,
    r.id AS rol_id,
    r.nombre AS rol_nombre
FROM usuarios u
JOIN usuario_rol ur ON ur.usuario_id = u.id
JOIN roles r ON r.id = ur.rol_id
WHERE u.username = ?


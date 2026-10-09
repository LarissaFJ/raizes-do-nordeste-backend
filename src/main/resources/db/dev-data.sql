INSERT INTO usuario (email, senha, role, cliente_id)
SELECT
    'admin@raizes.com',
    '$2a$10$.ATnO5CcjDbJ3M0iUeHYg.kgR1GIe8KAi6RBEUS41nAdZqAsfhiYq',
    'ADMIN',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM usuario
    WHERE role = 'ADMIN'
       OR email = 'admin@raizes.com'
);
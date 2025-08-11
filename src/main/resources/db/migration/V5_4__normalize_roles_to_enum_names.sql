-- Migration script to normalize role names to enum values
ALTER TABLE roles
    MODIFY COLUMN name VARCHAR(50) NOT NULL;

-- Normalise les données aux noms d'enum (ADMIN, MEMBER, ...)
UPDATE roles SET name = 'ADMIN'     WHERE LOWER(name) = 'admin';
UPDATE roles SET name = 'MEMBER'    WHERE LOWER(name) = 'member';
UPDATE roles SET name = 'AFFILIATE' WHERE LOWER(name) = 'affiliate';
UPDATE roles SET name = 'PRESS'     WHERE LOWER(name) = 'press';
UPDATE roles SET name = 'PRODUCER'  WHERE LOWER(name) = 'producer';

-- (Optionnel mais recommandé) Unicité du nom
-- Remplace l’index si tu en as déjà un avec un autre nom.
CREATE UNIQUE INDEX uk_roles_name ON roles(name);

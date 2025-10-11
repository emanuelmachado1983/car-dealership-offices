CREATE TABLE IF NOT EXISTS countries (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMP NULL
    );

CREATE TABLE IF NOT EXISTS provinces (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMP NULL,
    country_id BIGINT NOT NULL,
    CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES countries(id)
    );

CREATE TABLE IF NOT EXISTS localities (
                                          id BIGSERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMP NULL,
    province_id BIGINT NOT NULL,
    CONSTRAINT fk_province FOREIGN KEY (province_id) REFERENCES provinces(id)
    );

CREATE TABLE IF NOT EXISTS type_offices (
                                            id BIGSERIAL PRIMARY KEY,
                                            name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS offices (
                                       id BIGSERIAL PRIMARY KEY,
                                       id_country BIGINT NOT NULL,
                                       id_province BIGINT NOT NULL,
                                       id_locality BIGINT NOT NULL,
                                       address VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    opening_date TIMESTAMP NOT NULL,
    type_office_id BIGINT NOT NULL,
    deleted_at TIMESTAMP NULL,
    CONSTRAINT fk_type_office FOREIGN KEY (type_office_id) REFERENCES type_offices(id),
    CONSTRAINT fk_office_country FOREIGN KEY (id_country) REFERENCES countries(id),
    CONSTRAINT fk_office_province FOREIGN KEY (id_province) REFERENCES provinces(id),
    CONSTRAINT fk_office_locality FOREIGN KEY (id_locality) REFERENCES localities(id)
    );

CREATE TABLE IF NOT EXISTS delivery_schedules (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  office_from_id BIGINT NOT NULL,
                                                  office_to_id BIGINT NOT NULL,
                                                  days INT NOT NULL,
                                                  CONSTRAINT fk_office_from FOREIGN KEY (office_from_id) REFERENCES offices(id),
    CONSTRAINT fk_office_to FOREIGN KEY (office_to_id) REFERENCES offices(id)
    );

-- Inserts idempotentes (solo si no existen)
INSERT INTO type_offices (name)
SELECT 'Central' WHERE NOT EXISTS (SELECT 1 FROM type_offices WHERE name = 'Central');

INSERT INTO type_offices (name)
SELECT 'Concesionaria' WHERE NOT EXISTS (SELECT 1 FROM type_offices WHERE name = 'Concesionaria');

INSERT INTO countries (name, deleted_at)
SELECT 'Argentina', NULL WHERE NOT EXISTS (SELECT 1 FROM countries WHERE name = 'Argentina');

INSERT INTO provinces (name, deleted_at, country_id)
SELECT 'Capital Federal', NULL, (SELECT id FROM countries WHERE name='Argentina')
    WHERE NOT EXISTS (SELECT 1 FROM provinces WHERE name='Capital Federal' AND country_id = (SELECT id FROM countries WHERE name='Argentina'));

INSERT INTO provinces (name, deleted_at, country_id)
SELECT 'Buenos Aires', NULL, (SELECT id FROM countries WHERE name='Argentina')
    WHERE NOT EXISTS (SELECT 1 FROM provinces WHERE name='Buenos Aires' AND country_id = (SELECT id FROM countries WHERE name='Argentina'));

INSERT INTO provinces (name, deleted_at, country_id)
SELECT 'Neuquen', NULL, (SELECT id FROM countries WHERE name='Argentina')
    WHERE NOT EXISTS (SELECT 1 FROM provinces WHERE name='Neuquen' AND country_id = (SELECT id FROM countries WHERE name='Argentina'));

INSERT INTO localities (name, deleted_at, province_id)
SELECT 'Flores', NULL, (SELECT id FROM provinces WHERE name='Capital Federal')
    WHERE NOT EXISTS (SELECT 1 FROM localities WHERE name='Flores' AND province_id = (SELECT id FROM provinces WHERE name='Capital Federal'));

INSERT INTO localities (name, deleted_at, province_id)
SELECT 'Caballito', NULL, (SELECT id FROM provinces WHERE name='Capital Federal')
    WHERE NOT EXISTS (SELECT 1 FROM localities WHERE name='Caballito' AND province_id = (SELECT id FROM provinces WHERE name='Capital Federal'));

INSERT INTO localities (name, deleted_at, province_id)
SELECT 'Avellaneda', NULL, (SELECT id FROM provinces WHERE name='Buenos Aires')
    WHERE NOT EXISTS (SELECT 1 FROM localities WHERE name='Avellaneda' AND province_id = (SELECT id FROM provinces WHERE name='Buenos Aires'));

INSERT INTO localities (name, deleted_at, province_id)
SELECT 'Lanus', NULL, (SELECT id FROM provinces WHERE name='Buenos Aires')
    WHERE NOT EXISTS (SELECT 1 FROM localities WHERE name='Lanus' AND province_id = (SELECT id FROM provinces WHERE name='Buenos Aires'));

INSERT INTO localities (name, deleted_at, province_id)
SELECT 'Tigre', NULL, (SELECT id FROM provinces WHERE name='Buenos Aires')
    WHERE NOT EXISTS (SELECT 1 FROM localities WHERE name='Tigre' AND province_id = (SELECT id FROM provinces WHERE name='Buenos Aires'));

-- Offices: insertar con identificadores si no existen (por nombre + address)
INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
SELECT
    (SELECT id FROM countries WHERE name='Argentina'),
    (SELECT id FROM provinces WHERE name='Capital Federal'),
    (SELECT id FROM localities WHERE name='Flores'),
    'Rivadavia 6000',
    'sucursal de venta Rivadavia',
    TIMESTAMP '2025-06-15 15:15:00',
    (SELECT id FROM type_offices WHERE name='Central')
    WHERE NOT EXISTS (SELECT 1 FROM offices WHERE name='sucursal de venta Rivadavia' AND address='Rivadavia 6000');

INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
SELECT
    (SELECT id FROM countries WHERE name='Argentina'),
    (SELECT id FROM provinces WHERE name='Capital Federal'),
    (SELECT id FROM localities WHERE name='Caballito'),
    'Yerbal 2500',
    'sucursal de venta ejemplo1',
    TIMESTAMP '2025-06-15 16:15:00',
    (SELECT id FROM type_offices WHERE name='Concesionaria')
    WHERE NOT EXISTS (SELECT 1 FROM offices WHERE name='sucursal de venta ejemplo1' AND address='Yerbal 2500');

INSERT INTO offices (id_country, id_province, id_locality, address, name, opening_date, type_office_id)
SELECT
    (SELECT id FROM countries WHERE name='Argentina'),
    (SELECT id FROM provinces WHERE name='Buenos Aires'),
    (SELECT id FROM localities WHERE name='Avellaneda'),
    'Avellaneda 543',
    'sucursal de venta en Avellaneda',
    TIMESTAMP '2025-06-15 16:15:00',
    (SELECT id FROM type_offices WHERE name='Concesionaria')
    WHERE NOT EXISTS (SELECT 1 FROM offices WHERE name='sucursal de venta en Avellaneda' AND address='Avellaneda 543');

-- Delivery schedules (referenciando oficinas existentes)
INSERT INTO delivery_schedules (office_from_id, office_to_id, days)
SELECT
    (SELECT id FROM offices WHERE name='sucursal de venta Rivadavia'),
    (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1'),
    5
    WHERE NOT EXISTS (
  SELECT 1 FROM delivery_schedules ds
  WHERE ds.office_from_id = (SELECT id FROM offices WHERE name='sucursal de venta Rivadavia')
    AND ds.office_to_id = (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1')
    AND ds.days = 5
);

INSERT INTO delivery_schedules (office_from_id, office_to_id, days)
SELECT
    (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1'),
    (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1'),
    3
    WHERE NOT EXISTS (
  SELECT 1 FROM delivery_schedules ds
  WHERE ds.office_from_id = (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1')
    AND ds.office_to_id = (SELECT id FROM offices WHERE name='sucursal de venta ejemplo1')
    AND ds.days = 3
);
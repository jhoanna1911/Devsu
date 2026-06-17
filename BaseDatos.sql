-- Schema para clientes-service
CREATE SCHEMA IF NOT EXISTS clientes;

-- Schema para cuentas-service
CREATE SCHEMA IF NOT EXISTS cuentas;

-- =====================
-- SCHEMA: clientes
-- =====================

CREATE TABLE IF NOT EXISTS clientes.persona (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    edad INTEGER,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS clientes.cliente (
    id BIGINT PRIMARY KEY REFERENCES clientes.persona(id),
    cliente_id VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- =====================
-- SCHEMA: cuentas
-- =====================

CREATE TABLE IF NOT EXISTS cuentas.cliente_cache (
    id BIGINT PRIMARY KEY,
    cliente_id VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS cuentas.cuenta (
    id BIGSERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(20) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL DEFAULT 0,
    saldo_disponible DECIMAL(15,2) NOT NULL DEFAULT 0,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_cache_id BIGINT REFERENCES cuentas.cliente_cache(id)
);

CREATE TABLE IF NOT EXISTS cuentas.movimiento (
    id BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    cuenta_id BIGINT NOT NULL REFERENCES cuentas.cuenta(id)
);

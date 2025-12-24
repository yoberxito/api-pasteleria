-- =========================================================
-- ESQUEMA DE BASE DE DATOS - PASTELERÍA DULCES MOMENTOS
-- Motor: PostgreSQL
-- =========================================================

-- 1. TABLA DE CATEGORÍAS
-- Almacena los tipos de productos (Tortas, Bocaditos, etc.)
CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE, -- Ej: 'Tortas'
                            slug VARCHAR(50) NOT NULL UNIQUE, -- Ej: 'tortas' (útil para URLs)
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. TABLA DE PRODUCTOS
-- Contiene la información principal.
-- NOTA SOBRE IMÁGENES: Cambiado a BYTEA (Binary String) para guardar
-- el archivo de imagen crudo (BLOB) directamente en la base de datos.
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          category_id INTEGER REFERENCES categories(id),
                          name VARCHAR(150) NOT NULL,
                          description TEXT,

    -- Tipo BYTEA: El equivalente a BLOB en PostgreSQL.
    -- Almacena la secuencia de bytes real de la imagen (más eficiente que Base64).
                          image_data BYTEA,

                          base_price DECIMAL(10, 2) NOT NULL,
                          is_popular BOOLEAN DEFAULT FALSE,

    -- Especificaciones Técnicas (Información del Modal)
                          spec_ingredients TEXT,
                          spec_portions TEXT, -- Valor por defecto si no hay variante
                          spec_allergens TEXT,
                          spec_care TEXT,

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. TABLA DE VARIANTES / OPCIONES
-- Maneja los diferentes pesos y tamaños (Ej: 1/2 Kg vs 1 Kg)
-- Si un producto tiene filas aquí, el precio se toma de esta tabla.
CREATE TABLE product_variants (
                                  id SERIAL PRIMARY KEY,
                                  product_id INTEGER REFERENCES products(id) ON DELETE CASCADE,
                                  label VARCHAR(50) NOT NULL, -- Ej: "1/2 Kg", "1 Kg"
                                  price DECIMAL(10, 2) NOT NULL,
                                  portions_text VARCHAR(100), -- Ej: "8 - 10 porciones"
                                  sku VARCHAR(50) UNIQUE
);

-- ==========================================
-- CARGA DE DATOS INICIALES (SEED DATA)
-- Coincide con tu Frontend Angular
-- ==========================================

-- A. Insertar Categorías
INSERT INTO categories (name, slug) VALUES
                                        ('Tortas', 'tortas'),          -- ID 1
                                        ('Bocaditos', 'bocaditos'),    -- ID 2
                                        ('Postres', 'postres'),        -- ID 3
                                        ('Salados', 'salados'),        -- ID 4
                                        ('Personalizado', 'personalizado'); -- ID 5
-- Create the database
CREATE DATABASE MiniERP;

-- Connect to the MiniERP database
\c MiniERP;

-- Create Customer table
CREATE TABLE Customer (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- Create Product table
CREATE TABLE Product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    stock_qty INTEGER NOT NULL CHECK (stock_qty >= 0) DEFAULT 0
);

-- Create Orders table
CREATE TABLE Orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10, 2) NOT NULL CHECK (total_price >= 0),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE
);

-- Create OrderItem table
CREATE TABLE OrderItem (
    order_item_id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price_at_order_time DECIMAL(10, 2) NOT NULL CHECK (price_at_order_time >= 0),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Create indexes for better performance
CREATE INDEX idx_customer_email ON Customer(email);
CREATE INDEX idx_product_category ON Product(category);
CREATE INDEX idx_orders_customer ON Orders(customer_id);
CREATE INDEX idx_orders_date ON Orders(order_date);
CREATE INDEX idx_orderitem_order ON OrderItem(order_id);
CREATE INDEX idx_orderitem_product ON OrderItem(product_id);

-- Add a function to update stock quantity when an order is placed
CREATE OR REPLACE FUNCTION update_product_stock()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Product
    SET stock_qty = stock_qty - NEW.quantity
    WHERE product_id = NEW.product_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to automatically update stock when order items are added
CREATE TRIGGER trg_update_stock
AFTER INSERT ON OrderItem
FOR EACH ROW
EXECUTE FUNCTION update_product_stock();

-- Add a function to update order total when items are added
CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Orders
    SET total_price = (
        SELECT SUM(quantity * price_at_order_time)
        FROM OrderItem
        WHERE order_id = NEW.order_id
    )
    WHERE order_id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to automatically update order total when items are added/modified
CREATE TRIGGER trg_update_order_total
AFTER INSERT OR UPDATE ON OrderItem
FOR EACH ROW
EXECUTE FUNCTION update_order_total();

-- Create a view for order details
CREATE VIEW OrderDetails AS
SELECT
    o.order_id,
    o.order_date,
    c.customer_id,
    c.name AS customer_name,
    o.total_price,
    COUNT(oi.order_item_id) AS item_count
FROM Orders o
JOIN Customer c ON o.customer_id = c.customer_id
LEFT JOIN OrderItem oi ON o.order_id = oi.order_id
GROUP BY o.order_id, c.customer_id;

-- Create a view for product sales
CREATE VIEW ProductSales AS
SELECT
    p.product_id,
    p.name AS product_name,
    p.category,
    SUM(oi.quantity) AS total_sold,
    SUM(oi.quantity * oi.price_at_order_time) AS total_revenue
FROM Product p
LEFT JOIN OrderItem oi ON p.product_id = oi.product_id
GROUP BY p.product_id;

-- Add comments to tables and columns
COMMENT ON TABLE Customer IS 'Stores customer information';
COMMENT ON COLUMN Customer.name IS 'Full name of the customer';
COMMENT ON COLUMN Customer.phone IS 'Contact phone number';
COMMENT ON COLUMN Customer.email IS 'Email address for communication';

COMMENT ON TABLE Product IS 'Stores product information and inventory';
COMMENT ON COLUMN Product.price IS 'Current selling price of the product';
COMMENT ON COLUMN Product.stock_qty IS 'Current quantity available in stock';

COMMENT ON TABLE Orders IS 'Stores order headers with customer and total information';
COMMENT ON COLUMN Orders.total_price IS 'Calculated total price of all items in the order';

COMMENT ON TABLE OrderItem IS 'Stores individual items within each order';
COMMENT ON COLUMN OrderItem.price_at_order_time IS 'Price of the product at the time of ordering';
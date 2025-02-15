CREATE TABLE IF NOT EXISTS magic_link_token (
    id IDENTITY PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    expiration TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE
);

CREATE TABLE currency (
    id SERIAL PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

INSERT INTO currency (code, name) VALUES 
('AED', 'Dirham de Emiratos Árabes Unidos'),
('AFN', 'Afgani afgano'),
('ALL', 'Lek albanés'),
('AMD', 'Dram armenio'),
('ANG', 'Florín de las Antillas Neerlandesas'),
('AOA', 'Kwanza angoleño'),
('ARS', 'Peso argentino'),
('AUD', 'Dólar australiano'),
('AWG', 'Florín arubeño'),
('AZN', 'Manat azerbaiyano'),
('BAM', 'Marco convertible bosnioherzegovino'),
('BBD', 'Dólar de Barbados'),
('BDT', 'Taka bangladesí'),
('BGN', 'Lev búlgaro'),
('BHD', 'Dinar bahreiní'),
('BIF', 'Franco burundés'),
('BMD', 'Dólar de Bermudas'),
('BND', 'Dólar de Brunéi'),
('BOB', 'Boliviano'),
('BRL', 'Real brasileño'),
('BSD', 'Dólar bahameño'),
('BTC', 'Bitcoin'),
('BTN', 'Ngultrum butanés'),
('BWP', 'Pula botsuano'),
('BYN', 'Rublo bielorruso'),
('CAD', 'Dólar canadiense'),
('CHF', 'Franco suizo'),
('CLP', 'Peso chileno'),
('CNY', 'Yuan chino'),
('COP', 'Peso colombiano'),
('CRC', 'Colón costarricense'),
('CUP', 'Peso cubano'),
('CZK', 'Corona checa'),
('DKK', 'Corona danesa'),
('DOP', 'Peso dominicano'),
('DZD', 'Dinar argelino'),
('EGP', 'Libra egipcia'),
('EUR', 'Euro'),
('GBP', 'Libra esterlina'),
('HKD', 'Dólar de Hong Kong'),
('HNL', 'Lempira hondureño'),
('IDR', 'Rupia indonesia'),
('ILS', 'Nuevo shekel israelí'),
('INR', 'Rupia india'),
('JPY', 'Yen japonés'),
('KRW', 'Won surcoreano'),
('MXN', 'Peso mexicano'),
('MYR', 'Ringgit malasio'),
('NOK', 'Corona noruega'),
('NZD', 'Dólar neozelandés'),
('PEN', 'Sol peruano'),
('PHP', 'Peso filipino'),
('PLN', 'Złoty polaco'),
('RUB', 'Rublo ruso'),
('SAR', 'Riyal saudí'),
('SEK', 'Corona sueca'),
('SGD', 'Dólar de Singapur'),
('THB', 'Baht tailandés'),
('TRY', 'Lira turca'),
('UAH', 'Grivna ucraniana'),
('USD', 'Dólar estadounidense'),
('UYU', 'Peso uruguayo'),
('VND', 'Dong vietnamita'),
('ZAR', 'Rand sudafricano');

CREATE TABLE exchange_rate_log (
    id SERIAL PRIMARY KEY,
    base VARCHAR(10) NOT NULL,
    exchange_rate DECIMAL(18,6) NOT NULL,
	target VARCHAR(10) NOT NULL,
    query_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

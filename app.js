const express = require('express');
const dotenv = require('dotenv');
const csrf = require('csurf'); // <-- Importar csurf
const cookieParser = require('cookie-parser'); // <-- Necesario si usas cookies para CSRF tokens

const authRoutes = require('./routes/authRoutes');
const loanRoutes = require('./routes/loanRoutes');
const paymentRoutes = require('./routes/paymentRoutes');
const userRoutes = require('./routes/userRoutes');

dotenv.config();

const app = express();
app.use(express.json());
app.use(cookieParser()); // <-- Necesario si usas tokens CSRF en cookies

// Configurar CSRF middleware
const csrfProtection = csrf({ cookie: true });
app.use(csrfProtection);

// Middleware para enviar el token CSRF al frontend
app.use((req, res, next) => {
  res.cookie('XSRF-TOKEN', req.csrfToken());
  next();
});

// Tus rutas
app.use('/api/auth', authRoutes);
app.use('/api/loans', loanRoutes);
app.use('/api/payments', paymentRoutes);
app.use('/api/users', userRoutes);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});

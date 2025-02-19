const express = require('express');
const paymentController = require('../controllers/paymentController');
const { authenticate, authorize } = require('../middleware/authMiddleware');

const router = express.Router();

router.post('/make', authenticate, authorize(['user']), paymentController.makePayment);
router.get('/:loanId', authenticate, authorize(['user', 'admin']), paymentController.getPayments);

module.exports = router;
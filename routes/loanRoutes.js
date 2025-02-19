const express = require('express');
const loanController = require('../controllers/loanController');
const { authenticate, authorize } = require('../middleware/authMiddleware');

const router = express.Router();

router.post('/request', authenticate, authorize(['user']), loanController.requestLoan);
router.get('/', authenticate, authorize(['admin']), loanController.getLoans);

module.exports = router;
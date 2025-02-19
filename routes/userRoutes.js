const express = require('express');
const userController = require('../controllers/userController');
const { authenticate, authorize } = require('../middleware/authMiddleware');

const router = express.Router();

router.get('/', authenticate, authorize(['admin']), userController.getUsers);
router.put('/:id', authenticate, authorize(['admin']), userController.updateUser);

module.exports = router;
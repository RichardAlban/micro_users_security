const pool = require('../config/db');

exports.makePayment = async (req, res) => {
  const { loanId, amount } = req.body;

  try {
    const result = await pool.query(
      'INSERT INTO payments (loan_id, amount) VALUES ($1, $2) RETURNING *',
      [loanId, amount]
    );
    res.status(201).json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.getPayments = async (req, res) => {
  const { loanId } = req.params;

  try {
    const result = await pool.query('SELECT * FROM payments WHERE loan_id = $1', [loanId]);
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
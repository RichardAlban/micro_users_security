const pool = require('../config/db');
const { generateAmortizationTable } = require('../utils/amortization');

exports.requestLoan = async (req, res) => {
  const { userId, amount, term, interestRate } = req.body;

  try {
    const result = await pool.query(
      'INSERT INTO loans (user_id, amount, term, interest_rate) VALUES ($1, $2, $3, $4) RETURNING *',
      [userId, amount, term, interestRate]
    );
    const loan = result.rows[0];
    const amortizationTable = generateAmortizationTable(loan.amount, loan.term, loan.interest_rate);
    res.status(201).json({ loan, amortizationTable });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

exports.getLoans = async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM loans');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};
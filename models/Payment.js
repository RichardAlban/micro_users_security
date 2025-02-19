const pool = require('../config/db');

class Payment {
  static async create(loanId, amount) {
    const result = await pool.query(
      'INSERT INTO payments (loan_id, amount) VALUES ($1, $2) RETURNING *',
      [loanId, amount]
    );
    return result.rows[0];
  }

  static async findByLoanId(loanId) {
    const result = await pool.query('SELECT * FROM payments WHERE loan_id = $1', [loanId]);
    return result.rows;
  }
}

module.exports = Payment;
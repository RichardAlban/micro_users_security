const pool = require('../config/db');

class Loan {
  static async create(userId, amount, term, interestRate) {
    const result = await pool.query(
      'INSERT INTO loans (user_id, amount, term, interest_rate) VALUES ($1, $2, $3, $4) RETURNING *',
      [userId, amount, term, interestRate]
    );
    return result.rows[0];
  }

  static async findAll() {
    const result = await pool.query('SELECT * FROM loans');
    return result.rows;
  }
}

module.exports = Loan;
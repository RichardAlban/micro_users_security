const Loan = require('../models/Loan');

exports.requestLoan = async (userId, amount, term, interestRate) => {
  return await Loan.create(userId, amount, term, interestRate);
};

exports.getLoans = async () => {
  return await Loan.findAll();
};
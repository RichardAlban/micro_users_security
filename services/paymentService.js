const Payment = require('../models/Payment');

exports.makePayment = async (loanId, amount) => {
  return await Payment.create(loanId, amount);
};

exports.getPayments = async (loanId) => {
  return await Payment.findByLoanId(loanId);
};
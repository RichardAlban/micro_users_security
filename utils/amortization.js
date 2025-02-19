exports.generateAmortizationTable = (amount, term, interestRate) => {
    const monthlyInterestRate = interestRate / 100 / 12;
    const monthlyPayment = (amount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -term));
  
    const table = [];
    let balance = amount;
  
    for (let i = 1; i <= term; i++) {
      const interest = balance * monthlyInterestRate;
      const principal = monthlyPayment - interest;
      balance -= principal;
  
      table.push({
        month: i,
        payment: monthlyPayment.toFixed(2),
        principal: principal.toFixed(2),
        interest: interest.toFixed(2),
        balance: balance.toFixed(2),
      });
    }
  
    return table;
  };
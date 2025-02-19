const User = require('../models/User');

exports.getUsers = async () => {
  return await User.findAll();
};

exports.updateUser = async (id, name, email, role) => {
  return await User.update(id, name, email, role);
};
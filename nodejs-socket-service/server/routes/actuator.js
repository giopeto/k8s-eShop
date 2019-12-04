const express = require('express');
const router = express.Router();

const healtActuator = {
	'status':'UP',
	'engine': 'Node.js'
};

router.get('/health', (req, res) => {
  res.send(healtActuator);
});

module.exports = router;
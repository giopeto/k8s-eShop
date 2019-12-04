const express = require('express');
const router = express.Router();

router.get('/', (req, res) => {
  res.send({payload: 'api works'});
});

module.exports = router;
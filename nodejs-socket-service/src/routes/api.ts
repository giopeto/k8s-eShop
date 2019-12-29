import express from 'express';
const router = express.Router();

router.get('/', (req, res) => {
  res.send({payload: 'api works'});
});

export default router;
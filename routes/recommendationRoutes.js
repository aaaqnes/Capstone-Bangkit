const express = require('express');
const {
    getRecommendation, 
}= require('../controllers/modelControllers');

const router = express.Router();

router.get('/rec', getRecommendation);

module.exports = router;
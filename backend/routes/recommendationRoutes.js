const express = require('express');
const {
    getRecommendation, 
}= require('../controllers/recommendationControllers');

const router = express.Router();

router.get('/rec', getRecommendation);

module.exports = router;
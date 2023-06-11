const express = require("express");
const {
    addPlace,
    getAllPlace,
    getPlaceByID,
    updatePlace,
    deletePlace,
} = require("../controllers/placeControllers");

const router = express.Router();

router.post('/place', addPlace);
router.get('/place', getAllPlace);
router.get('/place/:place_id', getPlaceByID);
router.put('/place/:place_id', updatePlace);
router.delete('/place/:place_id', deletePlace);

module.exports = router;
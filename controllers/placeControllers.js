const admin = require("../config/firebase");
const Place = require("../models/placeModels");
const firestore = admin.firestore();

const addPlace = async (req, res) => {
    try {
        const data = req.body;
        const id = req.body.place_id;
        await firestore.collection('place').doc(id).set(data);
        res.send('Place saved successful');
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const getAllPlace = async (req, res) => {
    try {
        const places = await firestore.collection('place');
        const data = await places.get();
        const placesArray = [];
        if(data.empty) {
            res.status(404).send('No Places Data Found');
        } else {
            data.forEach(doc => {
                const place = new Place(
                    doc.data().place_id,
                    doc.data().place_name,
                    doc.data().place_loc,
                    doc.data().place_desc
                );
                placesArray.push(place);
            });
            res.send(placesArray);
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const getPlaceByID = async (req, res) => {
    try {
        const place_id = req.params.place_id;
        const place = await firestore.collection('place').doc(place_id);
        const data = await place.get();
        if(!data.exists) {
            res.status(404).send('Place with the given ID not found');
        }else {
            res.send(data.data());
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const updatePlace = async (req, res,) => {
    try {
        const place_id = req.params.place_id;
        const data = req.body;
        const place =  await firestore.collection('place').doc(place_id);
        await place.update(data);
        res.send('Place data updated successful');        
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const deletePlace = async (req, res,) => {
    try {
        const place_id = req.params.place_id;
        await firestore.collection('place').doc(place_id).delete();
        res.send('Place data deleted successful');
    } catch (error) {
        res.status(400).send(error.message);
    }
}



module.exports = {
    addPlace,
    getAllPlace,
    getPlaceByID,
    updatePlace,
    deletePlace,
};
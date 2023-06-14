const admin = require("../config/firebase");
const Itinerary = require("../models/itineraryModel");
const Place = require('../models/placeModel');
const firestore = admin.firestore();

const createItinerary =  async (req, res) => {
    try {
        const idToken = req.headers.authorization;
        const decodedToken = await admin.auth().verifyIdToken(idToken);
        const uid = decodedToken.uid;
        const itinerary_name = req.body.itinerary_name;
        const docName = uid + itinerary_name
        const docRef = firestore.collection('itinerary').doc(docName);
        const doc = await docRef.get();

        if(doc.exists) {
            console.log(doc)
            res.status(400).send('Itinerary with the given name already exist!');
        } else {
            const itineraryData = req.body;
            const itinerary = {
                ...itineraryData,
                user_Id: uid
            }
            docRef.set(itinerary);
            res.status(200).send('Itinerary created successfully');
        }
    } catch(error) {
        console.error('Error creating itinerary: ', error);
        res.status(500).send('Failed to create itinerary');
      };
}

const getAllItineraryByUser = async(req, res) => {
    try {
        const idToken = req.headers.authorization;
        const decodedToken = await admin.auth().verifyIdToken(idToken);
        const uid = decodedToken.uid;
        const docRef = firestore.collection('itinerary').where('user_Id', '==', uid);
        const data = await docRef.get();
        const itineraryArray = [];

        if (data.empty) {
            res.status(404).send('Itinerary data for this user not found!')
        } else {
            data.forEach((doc) => {
                const itineraries = new Itinerary(
                    doc.data().itinerary_date,
                    doc.data().itinerary_name,
                );
                itineraryArray.push(itineraries);
            });
            res.send(itineraryArray);
        }
    } catch(error) {
        res.status(400).send(error.message);
    }
}

const getSpecificItineraryByUser = async(req, res) => {
    try {
        const idToken = req.headers.authorization;
        const decodedToken = await admin.auth().verifyIdToken(idToken);
        const uid = decodedToken.uid;
        const itinerary_name = req.body.itinerary_name;
        const docName = uid + itinerary_name;
        const docRef = await firestore.collection('itinerary').doc(docName).listCollections();
        const itineraryArray = [];

        for (const subcollectionRef of docRef) {
            const subData = await subcollectionRef.get();
            subData.forEach((doc) => {
                const subs = new Place(
                    doc.data().place_id,
                    doc.data().place_name
                );
                itineraryArray.push(subs);
            });
        } res.send(itineraryArray);
    } catch (error) {
        res.status(400).send(error.message);
    }
}

const addPlaceToItinerary = async(req, res) => {
    try {
        const place_name = req.body.place_name;
        const place_id = req.body.place_id;
        const itinerary_name = req.body.itinerary_name;
        const idToken = req.headers.authorization;
        const decodedToken = await admin.auth().verifyIdToken(idToken);
        const uid = decodedToken.uid;
        const docName = uid + itinerary_name;
        const docRef = firestore.collection('itinerary').doc(docName).collection('place').doc(place_id);
        const doc = docRef.get();
        const data = {
            place_id: place_id,
            place_name : place_name,
        }

        if(doc.exists) {
            res.status(404).send(error.message)
        } else {
            docRef.set(data);
            console.log(data);
            res.send('Add place to Itenerary Success!')
        }
    } catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    createItinerary,
    getAllItineraryByUser,
    getSpecificItineraryByUser,
    addPlaceToItinerary
};

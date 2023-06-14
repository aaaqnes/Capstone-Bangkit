const admin = require('../config/firebase');
const firestore = admin.firestore();
const Place = require('../models/placeModel');

const getRecommendation = async(req, res) => {
    try {
        const docRef = await firestore.collection('place').where('worth', '==', true);
        const data = await docRef.get();
        const recArray = [];
        if(data.empty) {
            res.status(404).send('No data found!')
        } else {
            data.forEach(doc => {
                const recdata = new Place(
                    doc.data().place_id,
                    doc.data().place_name,
                    doc.data().place_loc,
                    doc.data().place_desc,
                    doc.data().place_imgurl
                );
                recArray.push(recdata);
            })
            res.send(recArray);
        }
    } catch(error) {
        console.log(error.message);
        res.status(400).send(error.message);
    }
}

module.exports = {
    getRecommendation
};
const database = require("../config/firebase");
const Planner = require("../models/plannerModels");

const createPlanner =  async (req, res, next) => {
    try {
        const data = req.body;
        await firestore.collection('planner').doc().set(data);
        res.send('Record saved successfuly');
    } catch (error) {
        res.status(400).send(error.message);
    }
}
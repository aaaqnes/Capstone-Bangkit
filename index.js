const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const PlaceRoutes = require("./routes/placeRoutes");

const app = express();

app.use(express.json());
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(PlaceRoutes);
app.post('/post-test', (req, res) => {
    console.log('Got body:', req.body);
    res.sendStatus(200);
});


const PORT = process.env.PORT || 5000
app.listen(PORT, () => {
    console.log("Server listening on " + PORT)
})
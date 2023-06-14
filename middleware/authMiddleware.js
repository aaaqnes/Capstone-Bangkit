const admin = require('../config/firebase');

const isAuthenticated = (req, res, next) => {
    const token = req.headers.authorization;

    if (!token) {
      res.status(401).json({ error: 'Unauthorized' });
    } else {
      admin.auth().verifyIdToken(token)
        .then((decodedToken) => {
          req.uid = decodedToken.uid;
          next();
        })
        .catch((error) => {
          console.error('Error', error);
          res.status(401).json({ error: 'Unauthorized' });
        });
    }
}

module.exports = {
    isAuthenticated,
};
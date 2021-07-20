require('dotenv').config();
var admin = require("firebase-admin");

var serviceAccount = {
	"type": process.env.FIREBASE_TYPE,
	"project_id": process.env.FIREBASE_PROJECT_ID,
	"private_key_id": process.env.FIREBASE_PRIVATE_KEY_ID,
	"private_key": process.env.FIREBASE_PRIVATE_KEY,
	"client_email": process.env.FIREBASE_CLIENT_EMAIL,
	"client_id": process.env.FIREBASE_CLIENT_ID,
	"auth_uri": process.env.FIREBASE_AUTH_URL,
	"token_uri": process.env.FIREBASE_TOKEN_URL,
	"auth_provider_x509_cert_url": process.env.FIREBASE_AUTH_PROVIDER,
	"client_x509_cert_url": process.env.FIREBASE_CERT_URL
};

admin.initializeApp({
  	credential: admin.credential.cert(serviceAccount),
  	databaseURL: "https://sprint-3-ppl.firebaseio.com"
});

module.exports = admin;
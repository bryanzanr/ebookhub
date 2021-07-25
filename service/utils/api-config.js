require('dotenv').config();

var {google} = require("googleapis");

// Load the service account key JSON file.
var serviceAccount = {
	"type": process.env.FIREBASE_TYPE,
	"project_id": process.env.FIREBASE_PROJECT_ID,
	"private_key_id": process.env.FIREBASE_PRIVATE_KEY_ID,
	"private_key": process.env.FIREBASE_PRIVATE_KEY.replace(/\\n/g, '\n'),
	"client_email": process.env.FIREBASE_CLIENT_EMAIL,
	"client_id": process.env.FIREBASE_CLIENT_ID,
	"auth_uri": process.env.FIREBASE_AUTH_URL,
	"token_uri": process.env.FIREBASE_TOKEN_URL,
	"auth_provider_x509_cert_url": process.env.FIREBASE_AUTH_PROVIDER,
	"client_x509_cert_url": process.env.FIREBASE_CERT_URL
};

// Define the required scopes.
var scopes = [
  "https://www.googleapis.com/auth/userinfo.email",
  "https://www.googleapis.com/auth/firebase.database"
];

// Authenticate a JWT client with the service account.
var jwtClient = new google.auth.JWT(
  serviceAccount.client_email,
  null,
  serviceAccount.private_key,
  scopes
);

const auth = async () => {
    // Use the JWT client to generate an access token.

    // console.log(jwtClient);

    // const [error, tokens] = await jwtClient.authorize();

    // jwtClient.authorize(function(error, tokens) {
	return await new Promise ( (callback) => {
		jwtClient.authorize( (error, tokens) => {
			if (error) {
				console.log("Error making request to generate access token:", error);
			} else if (tokens.access_token === null) {
				console.log("Provided service account does not have permission to generate access tokens");
			} else {
				var accessToken = tokens.access_token;
	
				// console.log(accessToken);

				callback(accessToken);
			
				// See the "Using the access token" section below for information
				// on how to use the access token to send authenticated requests to
				// the Realtime Database REST API.
			}
		});
	})
}

module.exports = {
    auth
}
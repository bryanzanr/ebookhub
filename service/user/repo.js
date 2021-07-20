const db = require('../utils/firebase-config').database();
const axios = require('axios')
const api = require('../utils/api-config');

let fetchData = async () => {
    try {
        const result = [];
        db.ref('sprint-3-ppl/users').once("value", snapshot => {
            if (snapshot.exists()) {
                snapshot.forEach(doc => {
                    result.push(doc);
                });
            }
        });
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let fetchUser = async () => {
    try {
        const auth = await api.auth();
        const url = `https://sprint-3-ppl.firebaseio.com/users.json?print=pretty`
        + `&access_token=${auth}`;
        const response = await axios.get(url);
        const result = response.data
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

module.exports = {
    fetchData,
    fetchUser
}
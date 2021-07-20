const userRepo = require('./repo');

let fetch = async (req, res) => {
    try {
        const response = await userRepo.fetchUser();
        res.status(200).send(response);
    } catch (error) {
        console.log(error.stack);
        res.status(500).send(error);
    }
}

module.exports = {
    fetch
}
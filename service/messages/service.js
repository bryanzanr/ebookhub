const messageRepo = require('./repo');
const messageTransformer = require('./transformer');

let fetch = async (req, res) => {
    try {
        let {
            page, 
            page_size
        } = req.query;
    
        if (!(Number(page) && Number(page_size))) {
            page = 1;
            page_size = 10;
        }
    
        const { result, total } = await messageRepo.fetchData(page, page_size);
        const response = await messageTransformer.transform(result, total, page, page_size);
        res.status(200).json(response);
    } catch (error) {
        console.log(error.stack);
        res.status(500).send(error);
    }
}

let fetchMessage = async (req, res) => {
    try {
        const result = await messageRepo.fetchDetailBySenderId(req.userAuth.id);
        const response = await messageTransformer.transformMessage(result);
        res.status(200).json(response);
    } catch (error) {
        console.log(error.stack);
        res.status(500).send(error);
    }
}

module.exports = {
    fetch,
    fetchMessage
}
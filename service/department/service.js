const departmentRepo = require('./repo');
const departmentTransformer = require('./transformer');

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
    
        const { result, total } = await departmentRepo.fetchData(page, page_size);
        const response = await departmentTransformer.transform(result, total, page, page_size);
        res.status(200).json(response);
    } catch (error) {
        console.log(error.stack);
        res.status(500).send(error);
    }
}

module.exports = {
    fetch
}
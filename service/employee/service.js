require('dotenv').config();
const employeeRepo = require('./repo');
const employeeTransformer = require('./transformer');

const HttpError = require('../utils/http-error');
const JWT = require('jsonwebtoken');

const Authorization = require('auth-header');

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
    
        const { result, total } = await employeeRepo.fetchData(page, page_size);
        const response = await employeeTransformer.transform(result, total, page, page_size);
        res.status(200).json(response);
    } catch (error) {
        console.log(error.stack);
        res.status(500).send(error);
    }
}

let checkAuth = async (req, res, next) => {
    const { headers, params } = req;
    if (!headers.Authorization) {
        res.status(422).json({
            error_description: "Token not provided",
            error_code: 422
        });
    }else {
        const auth = Authorization.parse(req.get('authorization')).params;
        req.allauth = auth;
        try {
            const response = await employeeRepo.fetchDetailById(params.resourceId);
            req.locals = {};
            req.locals.profile = response[0];
            next();
        } catch (error) {
            console.log(error.stack);
            res.status(500).send(error);
        }
    }
}

let validateToken = async (req, res, next) => {
    const headers = req;
    HttpError.initialize();
    if (!headers.authorization) {
        const err = HttpError.NotAuthorized('Token not provided');
        res.status(err.status).send(err);
    }else {
        try {
            const decoded = JWT.verify(headers.authorization, process.env.SECRET_KEY, { algorithms: ['RS256']});
            req.userAuth = decoded;
            next();
        } catch (error) {
            console.log(error.stack);
            res.status(500).send(err);
        }
    }
}

module.exports = {
    fetch,
    checkAuth,
    validateToken
}
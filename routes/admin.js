const express = require('express');
const cors = require('cors');

const attachmentService = require('../service/attachment/service');

const router = express.Router();

router.options('/attachment', cors());
router.get('/attachment', attachmentService.fetch);

module.exports = router;
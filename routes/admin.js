const express = require('express');
const cors = require('cors');

const attachmentService = require('../service/attachment/service');
const departmentService = require('../service/department/service');
const employeeService = require('../service/employee/service');
const messageService = require('../service/messages/service');
const userService = require('../service/user/service');

const router = express.Router();

router.options('/attachments', cors());
router.get('/attachments', attachmentService.fetch);
router.options('/departments', cors());
router.get('/departments', departmentService.fetch);
router.options('/employees', cors());
router.get('/employees', employeeService.fetch);
router.options('/messages', cors());
router.get('/messages', messageService.fetch);

router.options('/users', cors());
router.get('/users', userService.fetch);

module.exports = router;
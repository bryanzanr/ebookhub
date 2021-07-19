const express = require('express');

const attachmentService = require('../service/attachment/service');
const employeeService = require('../service/employee/service');
const messageService = require('../service/messages/service');

const router = express.Router();

router.post('/:resourceId/attachments', employeeService.checkAuth, attachmentService.add);
router.get('/:resourceId/messages', employeeService.validateToken, messageService.fetchMessage);
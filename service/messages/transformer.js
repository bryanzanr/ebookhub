const moment = require('moment');

const transform = (messages, total, page, page_size) => {
    const available_messages = [];
    messages.map(message => {
        available_messages.push({
            id: message.id,
            sender_id: message.sender_id,
            text: message.text,
            secret_key: message.secret_key,
            created_at: moment(message.created_at).format(),
            attachment_id: message.attachment_id
        });
    });
    return {
        available_messages,
        meta: {
            page_number: page,
            page_size: page_size,
            page_count: (Math.ceil(total / page_size)).toString(),
            total_count: total.toString()
        }
    }
}

const transformMessage = (message) => {
    return {
        data: {
            id: message.id,
            sender_id: message.sender_id,
            text: message.text,
            secret_key: message.secret_key,
            created_at: moment(message.created_at).format(),
            attachment_id: message.attachment_id
        }
    }
}

module.exports = {
    transform,
    transformMessage
}
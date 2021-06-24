const mysqlPool = require('../utils/mysql-db-pool').promise();

let fetchData = async (page, page_size) => {
    try {
        const limit = (page - 1) * page_size;
        const query = `
            SELECT id, sender_id, text, secret_key, created_at, attachment_id
            FROM messages
            LIMIT ${Number(page_size)} OFFSET ${Number(limit)}
        `;
        const queryTotal = `
            SELECT COUNT(*) AS total
            FROM messages
        `;
        const [result] = await mysqlPool.execute(query);
        const [totalResult] = await mysqlPool.execute(queryTotal);
        return {
            result,
            total: totalResult[0].total
        }
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let fetchDetailById = async (message_id) => {
    try {
        const query = `
            SELECT id, sender_id, text, secret_key, created_at, delivered_at, received_at, attachment_id
            FROM messages
            WHERE id = ${message_id}
        `;
        const [result] = await mysqlPool.execute(query);
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let fetchDetailBySenderId = async (sender_id) => {
    try {
        const query = `
            SELECT id, sender_id, text, secret_key, created_at, delivered_at, received_at, attachment_id
            FROM messages
            WHERE sender_id = ${sender_id}        
        `;
        const [result] = await mysqlPool.execute(query);
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let fetchDetailByAttachmentId = async (attachment_id) => {
    try {
        const query = `
            SELECT id, sender_id, text, secret_key, created_at, delivered_at, received_at, attachment_id
            FROM messages
            WHERE attachment_id = ${attachment_id}        
        `;
        const [result] = await mysqlPool.execute(query);
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

module.exports = {
    fetchData,
    fetchDetailById,
    fetchDetailBySenderId,
    fetchDetailByAttachmentId
}
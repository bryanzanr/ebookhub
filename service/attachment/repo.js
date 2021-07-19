const mysqlPool = require('../utils/mysql-db-pool').promise();
const mysql = require('mysql2');

let fetchData = async (page, page_size) => {
    try {
        const limit = (page - 1) * page_size;
        const query = `
            SELECT id, mime_type, metadata, content
            FROM attachments
            LIMIT ${Number(page_size)} OFFSET ${Number(limit)}
        `;
        const queryTotal = `
            SELECT COUNT(*) AS total
            FROM attachments
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

let fetchDetailById = async (attachment_id) => {
    try {
        const query = `
            SELECT id, mime_type, metadata, content
            FROM attachments
            WHERE id = ${attachment_id}
        `;
        const [result] = await mysqlPool.execute(query);
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let saveData = async (mime_type, metadata, content) => {
    try {
        const query = `
            INSERT INTO attachments(mime_type, metadata, content)
            VALUES(${mysql.escape(mime_type)}, ${mysql.escape(metadata)}, NULL)
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
    saveData
}
const mysqlPool = require('../utils/mysql-db-pool').promise();

let fetchData = async (page, page_size) => {
    try {
        const limit = (page - 1) * page_size;
        const query = `
            SELECT dept_id, dept_name, dept_location
            FROM department
            LIMIT ${Number(page_size)} OFFSET ${Number(limit)}
        `;
        const queryTotal = `
            SELECT COUNT(*) AS total
            FROM department
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

let fetchDetailById = async (department_id) => {
    try {
        const query = `
            SELECT dept_id, dept_name, dept_location
            FROM department
            WHERE dept_id = ${department_id}
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
    fetchDetailById
}
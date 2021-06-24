const mysqlPool = require('../utils/mysql-db-pool').promise();

let fetchData = async (page, page_size) => {
    try {
        const limit = (page - 1) * page_size;
        const query = `
            SELECT emp_id, emp_name, dept_id, salary
            FROM employee
            LIMIT ${Number(page_size)} OFFSET ${Number(limit)}
        `;
        const queryTotal = `
            SELECT COUNT(*) AS total
            FROM employee
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

let fetchDetailById = async (employee_id) => {
    try {
        const query = `
            SELECT emp_id, emp_name, dept_id, salary
            FROM employee
            WHERE emp_id = ${employee_id}
        `;
        const [result] = await mysqlPool.execute(query);
        return result;
    } catch (error) {
        console.log(error.stack);
        throw error;
    }
}

let fetchDetailByDeptId = async (dept_id) => {
    try {
        const query = `
            SELECT emp_id, emp_name, dept_id, salary
            FROM employee
            WHERE dept_id = ${dept_id}        
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
    fetchDetailByDeptId
}
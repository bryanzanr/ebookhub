const transform = (employees, total, page, page_size) => {
    const available_employees = [];
    employees.map(employee => {
        available_employees.push({
            id: employee.emp_id,
            dept_id: employee.dept_id,
            name: employee.emp_name,
            salary: employee.salary
        });
    });
    return {
        available_employees,
        meta: {
            page_number: page,
            page_size: page_size,
            page_count: (Math.ceil(total / page_size)).toString(),
            total_count: total.toString()
        }
    }
}

module.exports = {
    transform
}
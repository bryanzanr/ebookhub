const transform = (departments, total, page, page_size) => {
    const available_departments = [];
    departments.map(department => {
        available_departments.push({
            id: department.dept_id,
            name: department.dept_name,
            location: department.dept_location
        });
    });
    return {
        available_departments,
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
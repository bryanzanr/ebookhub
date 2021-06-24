const transform = (attachments, total, page, page_size) => {
    const available_attachments = [];
    attachments.map(attachment => {
        available_attachments.push({
            id: attachment.id,
            mime_type: attachment.mime_type,
            metadata: attachment.metadata
        });
    });
    return {
        available_attachments,
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
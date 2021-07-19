module.exports = class PublicOauthClient {
    constructor ({
        secretKey
    }) {
        this.secretKey = secretKey;
    }

    getTimestamp(date) {
        date = new Date(date);
        return Math.floor(date / 1000)
    }

    async attachments(cmd) {
        const response = await this.publicTest(`attachments`, cmd);
    
        console.log(JSON.stringify(response, null, 4));
    }

    async messages(cmd) {
        const response = await this.publicTest(`messages`, cmd);
    
        console.log(JSON.stringify(response, null, 4));
    }

    async publicTest(routing, cmd) {
        
    }
}
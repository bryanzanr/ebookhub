require('dotenv').config();
const auth = require('./public');
const method = new auth({
    secretKey: process.env.SECRET_KEY
});

(async () => {
    const testMethod = process.argv[2];
    const testCommand = process.argv[3];
    let result = '';

    switch(testMethod) {
        case 'attachments':
            result = await method.attachments(testCommand);
            break;
        case 'messages':
            result = await method.messages(testCommand);
            break;
      default:
          console.log('command not found!');
          break;
    }

    console.log(result); 
})();
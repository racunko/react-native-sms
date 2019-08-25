
# react-native-sms

Note: Limited functionality not appropriate for production use.

## Getting started

`$ yarn add react-native-sms`

### Mostly automatic installation

`$ react-native link react-native-sms`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.racunko.rn.SmsPackage;` to the imports at the top of the file
  - Add `new SmsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-sms'
    project(':react-native-sms').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-sms/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':react-native-sms')
    ```

## Usage
- Retrieve all SMS messages

```javascript
import Sms from 'react-native-sms';

let filter = {
  box: 'inbox', // 'inbox' (default), 'sent', 'draft', 'outbox', 'failed', 'queued', and '' for all
};

const failCallback = (error) => {
  console.log("OH Snap: " + error)
};

const successCallback = (smsCount, smsList) => {
  console.log('Count: ', count);
  console.log('List: ', smsList);
  let messages = JSON.parse(smsList);
  for (let message of messages) {
    console.log(`id: ${message._id}, date: ${message.date}, address: ${message.address}, body: ${message.body}`);
  }
}

Sms.list(JSON.stringify(filter), failCallback, successCallback);

/* 
Each sms will be represents by a JSON object represented below

{
  "_id": 1234,
  "thread_id": 3,
  "address": "2900",
  "person": -1,
  "date": 1365053816196,
  "date_sent": 0,
  "protocol": 0,
  "read": 1,
  "status": -1,
  "type": 1,
  "body": "Hello There, I am an SMS",
  "service_center": "+60162999922",
  "locked": 0,
  "error_code": -1,
  "sub_id": -1,
  "seen": 1,
  "deletable": 0,
  "sim_slot": 0,
  "hidden": 0,
  "app_id": 0,
  "msg_id": 0,
  "reserved": 0,
  "pri": 0,
  "teleservice_id": 0,
  "svc_cmd": 0,
  "roam_pending": 0,
  "spam_report": 0,
  "secret_mode": 0,
  "safe_message": 0,
  "favorite": 0
}
*/
```

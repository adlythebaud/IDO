const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref("daily")
    .onWrite(event => {
        console.log("goal added to daily");
        return null;
        // this is from a tutorial. Our code would be different.
//        const message = event.data.current.val();
//        const senderUid = message.from;
//        const receiverUid = message.to;
//        const promises = [];
//
//        if (senderUid == receiverUid) {
//            //if sender is receiver, don't send notification
//            promises.push(event.data.current.ref.remove());
//            return Promise.all(promises);
//        }
//
//        const getInstanceIdPromise = admin.database().ref(`/users/${receiverUid}/instanceId`).once('value');
//        const getReceiverUidPromise = admin.auth().getUser(receiverUid);
//
//        return Promise.all([getInstanceIdPromise, getReceiverUidPromise]).then(results => {
//            const instanceId = results[0].val();
//            const receiver = results[1];
//            console.log('notifying ' + receiverUid + ' about ' + message.body + ' from ' + senderUid);
//
//            const payload = {
//                notification: {
//                    title: receiver.displayName,
//                    body: message.body,
//                    icon: receiver.photoURL
//                }
//            };
//
//            admin.messaging().sendToDevice(instanceId, payload)
//                .then(function (response) {
//                    console.log("Successfully sent message:", response);
//                })
//                .catch(function (error) {
//                    console.log("Error sending message:", error);
//                });
//        });
    });
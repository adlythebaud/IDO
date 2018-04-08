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

exports.sendNotif = functions.auth.user().onCreate((user) => {

//exports.makeUppercase = functions.database.ref('/messages/{pushId}/original')
//    .onWrite((change, context) => {
        console.log("new user created");
//        //TODO: register this user to receive a push notification every night. test sending 2 notifs.
//        var uid = user.uid;
//        const promises = [];
//
//
//        const payload = {
//            notification: {
//                title: "Test Message",
//                body: "this is a test push notification."
//            }
//        };
//
//        admin.messaging().sendToDevice(uid, payload)
//            .then(function(response) {
//                console.log("Successfully sent message:", response);
//                return null;
//            })
//            .catch(function(error) {
//                console.log("Error sending message:", error);
//            });

        return null;

    });


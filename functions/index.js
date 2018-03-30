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
    });

exports.addToDatabase = functions.auth.user().onCreate(function(event) {
    // get the uUID

    var uid = event.data.uid;

    var user = event.data;
//
//    if (user != null) {
//        user.updateProfile({
//            displayName: name
//        }).then(function() {
//            console.log("update to user profile successful");
//        }).catch(function(error) {
//            console.log("update to user profile failed");
//        });
//    }

    // var newUserNode = admin.database.ref('/users/{uid}');

//    newUserNode.set(user);
    return null;

});
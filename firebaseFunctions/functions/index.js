var functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.getFlashCards = functions.https.onRequest((req, res) => {
	return admin.database().ref('flashcards/').once('value', (flashCards) => {
		res.json(flashCards.val())
		/*flashCards.forEach(function(flashCard) {
			// key will be "ada" the first time and "alan" the second time
			var key = flashCard.key;
			// childData will be the actual contents of the child
			var item = flashCard.val();

			res.json(flashCard.val())
		});*/
	});
});

exports.getUsers = functions.https.onRequest((req, res) => {
	return admin.database().ref('users').once('value', (snapshot) => {

		snapshot.forEach((userSnapshot) => {
			var user = userSnapshot.val();
			var answeredExams = user.answeredExams;

			console.log("AnsweredExams: " + answeredExams);

			// key will be "ada" the first time and "alan" the second time
			//var key = userSnapshot.key;
			// childData will be the actual contents of the child
			var childData = userSnapshot.val();
			console.log(childData);
		});

		res.json(snapshot.val());
	});
});
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
	return admin.database().ref('users/').once('value', (users) => {
		//res.json(users.val())
		users.forEach(function(userSnapshot) {
			var user = userSnapshot.val();

			console.log('User: ' + user);

			//var answeredExams = user.answeredExams.val();
			//res.json(user.val())
		});
	});
});
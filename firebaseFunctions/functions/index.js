var functions = require('firebase-functions');
const admin = require('firebase-admin');

var express = require('express');
var app = express();

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

exports.getAverage = functions.https.onRequest((req, res) => {
	return admin.database().ref('users').once('value', (snapshot) => {

		var totalUsersE1 = 0;
		var totalUsersE2 = 0;

		var totalAnsweredQuestionsE1 = 0;
		var totalAnsweredQuestionsE2 = 0;

		var totalCorrectE1 = 0;
		var totalCorrectE2 = 0;

		var totalIncorrectE1 = 0;
		var totalIncorrectE2 = 0;

		var totalQuestionsE1 = 0;
		var totalQuestionsE2 = 0;

		var best1 = 0;
		var best2 = 0;

		var average1 = 0;
		var average2 = 0;

		snapshot.forEach((userSnapshot) => {
			var answeredExams = userSnapshot.child("answeredExams");

			console.log("AnsweredExams: " + answeredExams);

			var e1 = answeredExams.child("e1");
			var e2 = answeredExams.child("e2");
			if(e1.exists()) {
				totalUsersE1++;
				console.log("e1 exist");

				var aq1 = e1.child("answered_questions");
				var cr1 = e1.child("correct");
				var ic1 = e1.child("incorrect");
				var tq1 = e1.child("total_questions");

				if(cr1.val() > best1) {
					best1 = cr1.val();
				}

				totalAnsweredQuestionsE1 = totalAnsweredQuestionsE1 + aq1.val();
				totalCorrectE1 = totalCorrectE1 + cr1.val();
				totalIncorrectE1 = totalIncorrectE1 + ic1.val();
				totalQuestionsE1 = totalQuestionsE1 + tq1.val();
			} else {
				console.log("e1 no exist");
			}

			if(e2.exists()) {
				totalUsersE2++;
				console.log("e2 exist");

				var aq2 = e2.child("answered_questions");
				var cr2 = e2.child("correct");
				var ic2 = e2.child("incorrect");
				var tq2 = e2.child("total_questions");

				if(cr2.val() > best2) {
					best2 = cr2.val();
				}

				totalAnsweredQuestionsE2 = totalAnsweredQuestionsE2 + aq2.val();
				totalCorrectE2 = totalCorrectE2 + cr2.val();
				totalIncorrectE2 = totalIncorrectE2 + ic2.val();
				totalQuestionsE2 = totalQuestionsE2 + tq2.val();
			} else {
				console.log("e2 no exist");
			}
		});

		console.log("Total Users e1 that exist: " + totalUsersE1);
		console.log("Total Users e2 that exist: " + totalUsersE2);

		console.log("Total AnsweredQuestionsE1: " + totalAnsweredQuestionsE1);
		console.log("Total AnsweredQuestionsE2: " + totalAnsweredQuestionsE2);

		console.log("Total CorrectE1: " + totalCorrectE1);
		console.log("Total CorrectE2: " + totalCorrectE2);

		console.log("Total IncorrectE1: " + totalIncorrectE1);
		console.log("Total IncorrectE2: " + totalIncorrectE2);

		console.log("Total QuestionsE1: " + totalQuestionsE1);
		console.log("Total QuestionsE2: " + totalQuestionsE2);

		let e1 = {
			'totalUsers': totalUsersE1,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE1,
			'totalCorrect' : totalCorrectE1,
			'totalIncorrect' : totalIncorrectE1,
			'totalQuestions' : totalQuestionsE1
		};

		let e2 = {
			'totalUsers': totalUsersE2,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE2,
			'totalCorrect' : totalCorrectE2,
			'totalIncorrect' : totalIncorrectE2,
			'totalQuestions' : totalQuestionsE2
		};

		average1 = totalCorrectE1 / totalUsersE1;
		average2 = totalCorrectE2 / totalUsersE2;

		let result1 = {
			'best' : best1,
			'average' : average1
		}

		let result2 = {
			'best' : best2,
			'average' : average2
		}

		let exams = {
			'e1' : result1,
			'e2' : result2
		}

		let score = {
			'score' : exams,
		}

		//var array = [];

		//array.push(total1);
		//array.push(total2);

		res.json(score);
	});
});

app.get('/userId/:userId', (req, res) => {
  var userId = req.params.userId;
  console.log("Received userId: " + userId);

  return admin.database().ref('users').once('value', (snapshot) => {
  		var userAverageE1 = 0;
  		var userAverageE2 = 0;

		var totalUsersE1 = 0;
		var totalUsersE2 = 0;

		var totalAnsweredQuestionsE1 = 0;
		var totalAnsweredQuestionsE2 = 0;

		var totalCorrectE1 = 0;
		var totalCorrectE2 = 0;

		var totalIncorrectE1 = 0;
		var totalIncorrectE2 = 0;

		var totalQuestionsE1 = 0;
		var totalQuestionsE2 = 0;

		var best1 = 0;
		var best2 = 0;

		var average1 = 0;
		var average2 = 0;

		var isUser = false;

		snapshot.forEach((userSnapshot) => {
			var answeredExams = userSnapshot.child("answeredExams");
			console.log("UserJSON: " + userSnapshot.key);
			if(userId === userSnapshot.key) {
				console.log("This is me: " + userId);
				isUser = true;
			} else {
				isUser = false;
			}

			console.log("AnsweredExams: " + answeredExams);

			var e1 = answeredExams.child("e1");
			var e2 = answeredExams.child("e2");
			if(e1.exists()) {
				totalUsersE1++;
				console.log("e1 exist");

				var aq1 = e1.child("answered_questions");
				var cr1 = e1.child("correct");
				var ic1 = e1.child("incorrect");
				var tq1 = e1.child("total_questions");

				if(cr1.val() > best1) {
					best1 = cr1.val();
				}

				if(isUser) {
					console.log("isUser Exam1: " + cr1.val());
					userAverageE1 = cr1.val();
				}

				totalAnsweredQuestionsE1 = totalAnsweredQuestionsE1 + aq1.val();
				totalCorrectE1 = totalCorrectE1 + cr1.val();
				totalIncorrectE1 = totalIncorrectE1 + ic1.val();
				totalQuestionsE1 = totalQuestionsE1 + tq1.val();
			} else {
				console.log("e1 no exist");
			}

			if(e2.exists()) {
				totalUsersE2++;
				console.log("e2 exist");

				var aq2 = e2.child("answered_questions");
				var cr2 = e2.child("correct");
				var ic2 = e2.child("incorrect");
				var tq2 = e2.child("total_questions");

				if(cr2.val() > best2) {
					best2 = cr2.val();
				}

				if(isUser) {
					console.log("isUser Exam2: " + cr2.val());
					userAverageE2 = cr2.val();
				}

				totalAnsweredQuestionsE2 = totalAnsweredQuestionsE2 + aq2.val();
				totalCorrectE2 = totalCorrectE2 + cr2.val();
				totalIncorrectE2 = totalIncorrectE2 + ic2.val();
				totalQuestionsE2 = totalQuestionsE2 + tq2.val();
			} else {
				console.log("e2 no exist");
			}
		});

		console.log("Total Users e1 that exist: " + totalUsersE1);
		console.log("Total Users e2 that exist: " + totalUsersE2);

		console.log("Total AnsweredQuestionsE1: " + totalAnsweredQuestionsE1);
		console.log("Total AnsweredQuestionsE2: " + totalAnsweredQuestionsE2);

		console.log("Total CorrectE1: " + totalCorrectE1);
		console.log("Total CorrectE2: " + totalCorrectE2);

		console.log("Total IncorrectE1: " + totalIncorrectE1);
		console.log("Total IncorrectE2: " + totalIncorrectE2);

		console.log("Total QuestionsE1: " + totalQuestionsE1);
		console.log("Total QuestionsE2: " + totalQuestionsE2);

		console.log("User Average E1: " + userAverageE1);
		console.log("User Average E2: " + userAverageE2);

		let e1 = {
			'totalUsers': totalUsersE1,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE1,
			'totalCorrect' : totalCorrectE1,
			'totalIncorrect' : totalIncorrectE1,
			'totalQuestions' : totalQuestionsE1
		};

		let e2 = {
			'totalUsers': totalUsersE2,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE2,
			'totalCorrect' : totalCorrectE2,
			'totalIncorrect' : totalIncorrectE2,
			'totalQuestions' : totalQuestionsE2
		};

		average1 = totalCorrectE1 / totalUsersE1;
		average2 = totalCorrectE2 / totalUsersE2;

		let result1 = {
			'user' : userAverageE1,
			'best' : best1,
			'average' : average1
		}

		let result2 = {
			'user' : userAverageE2,
			'best' : best2,
			'average' : average2
		}

		var exams1 = {
			'e1' : result1
		};

		var exams2 = {
			'e2' : result2
		};

		var array = [];

		array.push(exams1);
		array.push(exams2);

		let score = {
			'score' : array,
		}

		res.json(score);
	});
});

// Expose Express API as a single Cloud Function:
exports.getAverageByUser = functions.https.onRequest(app);
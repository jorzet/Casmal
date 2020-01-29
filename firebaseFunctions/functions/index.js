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
		var userAverageE3 = 0;
  		var userAverageE4 = 0;

		var totalUsersE1 = 0;
		var totalUsersE2 = 0;
		var totalUsersE3 = 0;
		var totalUsersE4 = 0;

		var totalAnsweredQuestionsE1 = 0;
		var totalAnsweredQuestionsE2 = 0;
		var totalAnsweredQuestionsE3 = 0;
		var totalAnsweredQuestionsE4 = 0;

		var totalCorrectE1 = 0;
		var totalCorrectE2 = 0;
		var totalCorrectE3 = 0;
		var totalCorrectE4 = 0;

		var totalIncorrectE1 = 0;
		var totalIncorrectE2 = 0;
		var totalIncorrectE3 = 0;
		var totalIncorrectE4 = 0;

		var totalQuestionsE1 = 0;
		var totalQuestionsE2 = 0;
		var totalQuestionsE3 = 0;
		var totalQuestionsE4 = 0;

		var best1 = 0;
		var best2 = 0;
		var best3 = 0;
		var best4 = 0;

		var average1 = 0;
		var average2 = 0;
		var average3 = 0;
		var average4 = 0;

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
			var e3 = answeredExams.child("e3");
			var e4 = answeredExams.child("e4");

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

			if(e3.exists()) {
				totalUsersE3++;
				console.log("e3 exist");

				var aq3 = e3.child("answered_questions");
				var cr3 = e3.child("correct");
				var ic3 = e3.child("incorrect");
				var tq3 = e3.child("total_questions");

				if(cr3.val() > best3) {
					best3 = cr3.val();
				}

				if(isUser) {
					console.log("isUser Exam3: " + cr3.val());
					userAverageE3 = cr3.val();
				}

				totalAnsweredQuestionsE3 = totalAnsweredQuestionsE3 + aq3.val();
				totalCorrectE3 = totalCorrectE3 + cr3.val();
				totalIncorrectE3 = totalIncorrectE3 + ic3.val();
				totalQuestionsE3 = totalQuestionsE3 + tq3.val();
			} else {
				console.log("e3 no exist");
			}

			if(e4.exists()) {
				totalUsersE4++;
				console.log("e4 exist");

				var aq4 = e4.child("answered_questions");
				var cr4 = e4.child("correct");
				var ic4 = e4.child("incorrect");
				var tq4 = e4.child("total_questions");

				if(cr4.val() > best4) {
					best4 = cr4.val();
				}

				if(isUser) {
					console.log("isUser Exam4: " + cr4.val());
					userAverageE4 = cr4.val();
				}

				totalAnsweredQuestionsE44= totalAnsweredQuestionsE4 + aq4.val();
				totalCorrectE4 = totalCorrectE4 + cr4.val();
				totalIncorrectE4 = totalIncorrectE4 + ic4.val();
				totalQuestionsE4 = totalQuestionsE4 + tq4.val();
			} else {
				console.log("e4 no exist");
			}
		});

		console.log("Total Users e1 that exist: " + totalUsersE1);
		console.log("Total Users e2 that exist: " + totalUsersE2);
		console.log("Total Users e3 that exist: " + totalUsersE3);
		console.log("Total Users e4 that exist: " + totalUsersE4);

		console.log("Total AnsweredQuestionsE1: " + totalAnsweredQuestionsE1);
		console.log("Total AnsweredQuestionsE2: " + totalAnsweredQuestionsE2);
		console.log("Total AnsweredQuestionsE3: " + totalAnsweredQuestionsE3);
		console.log("Total AnsweredQuestionsE4: " + totalAnsweredQuestionsE4);

		console.log("Total CorrectE1: " + totalCorrectE1);
		console.log("Total CorrectE2: " + totalCorrectE2);
		console.log("Total CorrectE3: " + totalCorrectE3);
		console.log("Total CorrectE4: " + totalCorrectE4);

		console.log("Total IncorrectE1: " + totalIncorrectE1);
		console.log("Total IncorrectE2: " + totalIncorrectE2);
		console.log("Total IncorrectE3: " + totalIncorrectE3);
		console.log("Total IncorrectE4: " + totalIncorrectE4);

		console.log("Total QuestionsE1: " + totalQuestionsE1);
		console.log("Total QuestionsE2: " + totalQuestionsE2);
		console.log("Total QuestionsE3: " + totalQuestionsE3);
		console.log("Total QuestionsE4: " + totalQuestionsE4);

		console.log("User Average E1: " + userAverageE1);
		console.log("User Average E2: " + userAverageE2);
		console.log("User Average E3: " + userAverageE3);
		console.log("User Average E4: " + userAverageE4);

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

		let e3 = {
			'totalUsers': totalUsersE3,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE3,
			'totalCorrect' : totalCorrectE3,
			'totalIncorrect' : totalIncorrectE3,
			'totalQuestions' : totalQuestionsE3
		};

		let e4 = {
			'totalUsers': totalUsersE4,
			'totalAnsweredQuestions' : totalAnsweredQuestionsE4,
			'totalCorrect' : totalCorrectE4,
			'totalIncorrect' : totalIncorrectE4,
			'totalQuestions' : totalQuestionsE4
		};

		average1 = totalCorrectE1 / totalUsersE1;
		average2 = totalCorrectE2 / totalUsersE2;
		average3 = totalCorrectE3 / totalUsersE3;
		average4 = totalCorrectE4 / totalUsersE4;

		if(average1 === null) {
			average1 = 0;
		}

		if(average2 ===null) {
			average2 = 0;
		}

		if(average3 === null) {
			average3 = 0;
		}

		if(average4 === null) {
			average4 = 0;
		}

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

		let result3 = {
			'user' : userAverageE3,
			'best' : best3,
			'average' : average3
		}

		let result4 = {
			'user' : userAverageE4,
			'best' : best4,
			'average' : average4
		}

		var exams1 = {
			'e1' : result1
		};

		var exams2 = {
			'e2' : result2
		};

		var exams3 = {
			'e3' : result3
		};

		var exams4 = {
			'e4' : result4
		};

		var array = [];

		array.push(exams1);
		array.push(exams2);
		array.push(exams3);
		array.push(exams4);

		let score = {
			'score' : array,
		}

		res.json(score);
	});
});

// Expose Express API as a single Cloud Function:
exports.getAverageByUser = functions.https.onRequest(app);
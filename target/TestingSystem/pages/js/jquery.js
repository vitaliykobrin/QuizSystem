$(document).ready(function() {
    var questionId;
    var questionsMax;
    var questionsCounter = 1;
    var time;
    var timeLimit;
    var intervalId;

    getQuestion();
    getLimits();
    startTimer();
    $('#questionDiv').show();
    $('#resultDiv').hide();

    $('#sendAnswer').click(function() {
        var userAnswer = $(".answer:checked").val();
        if (userAnswer != undefined) {
            $('#messageText').text('');
            time = timeLimit;
            if (!checkQuestionsCount()) {
                getResult();
                return;
            }
            getQuestion(userAnswer);
        } else {
            $('#messageText').text('Please, select answer');
        }
    });

    function getLimits() {
        $.get('/quiz/getLimits', function(limits) {
            questionsMax = limits[0];
            $('#counter').text('1 of ' + questionsMax);
            timeLimit = limits[1];
            time = limits[1];
            $('#timer').text('00:' + time);
        });
    }

    function getQuestion(answer) {
        $.getJSON('/quiz/getQuestion', {answer: answer, questionId: questionId}, function(question) {
            var points = "";
            $('#questionText').text(question.questionText);
            questionId = question.id;

            var i = 0;
            $.each(question.answers, function(index, value) {
                points += "<p><input type='radio' class='answer' name='answer' value=" + i + ">" + value.text + "</p>";
                i++;
            });
            $('#answersDiv').html(points);
        });
    }

    function getResult() {
        clearInterval(intervalId);
        $.getJSON('/quiz/getResult', function (result) {
            $('#questionDiv').hide();
            $('#resultDiv').show();
            $('#spentTime').text(result.spentTime);
            $('#rightAnswers').text(result.rightAnswers + '/' + result.questionsCount);
            $('#points').text(result.points);
            $('#attempts').text(result.attempts);
            $('#messageText').text(result.messageText);
        })
    }

    function checkQuestionsCount() {
        if (questionsCounter == questionsMax) {
            return false;
        }
        $('#counter').text(++questionsCounter + ' of ' + questionsMax);
        if (questionsCounter == questionsMax) {
            $('#sendAnswer').val('Done');
        }
        return true;
    }

    function startTimer() {
        intervalId = setInterval(function () {
            $('#timer').text((time < 10 ? '00:0' : '00:') + time);
            if (time == 0) {
                if (checkQuestionsCount()) {
                    getQuestion();
                } else {
                    getResult();
                }
                time = timeLimit;
            }
            time--;
        }, 1000);
    }
});

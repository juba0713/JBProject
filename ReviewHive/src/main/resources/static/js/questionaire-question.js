
class Question{
	
	constructor(questionNo,
		questionType,
		question,
		questionImage,
		answer,
	) {
    this.questionNo = questionNo;
    this.questionType = questionType;
    this.question = question;
    this.questionImage = questionImage;
    this.answer = answer;
  }
}

class Answer{
	constructor(answer){
		this.answer = answer;
	}
}

var questionList = [];

const contentBodyQuestion = document.querySelector('.content-body-question');

const MULTIPLE_CHOICE = '1';
const TRUE_FALSE = '2';
const IDENTIFICATION = '3';

const promptQuestionCount = document.querySelector('.prompt-question-count');
const promptQuestionType = document.querySelector('.prompt-question-type');
const totalQuestion = document.querySelector('.total-question');

const form = document.querySelector('#form');

let currentTotalQuestion = 1;


document.querySelector('.add-question-btn').addEventListener('click', function(){
		
	const questionCount = promptQuestionCount.value;
	const questionType = promptQuestionType.value;
	
	console.log("Question Type: " + questionType);
	console.log("Question Count: " + questionCount);
		
	totalQuestion.innerHTML = Number(totalQuestion.innerHTML) + Number(questionCount);

	for(let count = 1; count <= Number(questionCount); count++){
		
		let container = createQuestionContainer(currentTotalQuestion, questionType);
		
		contentBodyQuestion.appendChild(container);
						
		generateQuestionHidden(
			currentTotalQuestion,
			questionType,
			
		);
		
		currentTotalQuestion++;
	}
});

function generateQuestionHidden(questionNo,
		questionType
	){

    const hiddenQuestionNo = document.createElement('input');
    hiddenQuestionNo.type = 'hidden';
    hiddenQuestionNo.name = `questions[${questionNo-1}].questionNo`;
    hiddenQuestionNo.value = questionNo;

    const hiddenQuestionType = document.createElement('input');
    hiddenQuestionType.type = 'hidden';
    hiddenQuestionType.name = `questions[${questionNo-1}].questionType`;
    hiddenQuestionType.value = questionType;
      
    // Append the inputs to the form
    form.appendChild(hiddenQuestionNo);
    form.appendChild(hiddenQuestionType);
}

document.querySelector('.save-btn').addEventListener('click', function(){
	
	  form.submit(); 
});


function createQuestionContainer(questionNo, questionType){
	
	const container = document.createElement('div');
	container.classList.add('question-container');
	container.classList.add(`question-container-${questionNo}`);
	
	switch(questionType){
		case MULTIPLE_CHOICE: // Multiple Choice
			container.setAttribute('questionType', questionType);
			container.setAttribute('answerCount', 4);
			container.innerHTML = `<div class="question-image">
						<img src="/images/no-image.png" class="question-uploaded-image">
					</div>
					<div class="question-content">
						<div class="question">
							<div class="question-number">Question ${questionNo} - Multiple Choice</div>
							<textarea name="questions[${questionNo-1}].question"></textarea>
							<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image">
						</div>
						<div class="answers answers-${questionNo}">
							<div>
								<input type="radio" disabled/>
								<input type="text" name="questions[${questionNo-1}].answers[0].answer" value="Option"/>
								<img class="remove-btn remove-option" src="/icons/warning.svg"/>
							</div>
							<div>
								<input type="radio" disabled/>
								<input type="text" name="questions[${questionNo-1}].answers[1].answer" value="Option"/>
								<img class="remove-btn remove-option" src="/icons/warning.svg"/>
							</div>
							<div>
								<input type="radio" disabled/>
								<input type="text" name="questions[${questionNo-1}].answers[2].answer" value="Option"/>
								<img class="remove-btn remove-option" src="/icons/warning.svg"/>
							</div>
							<div>
								<input type="radio" disabled/>
								<input type="text" name="questions[${questionNo-1}].answers[3].answer" value="Option"/>
								<img class="remove-btn remove-option" src="/icons/warning.svg"/>
							</div>
						</div>
					</div>							
					<div class="question-settings">
						<img src="/icons/x-square.svg"/>
						<img src="/icons/add-square.svg" class="add-option" />
					</div>`;				
			break;
					
		case TRUE_FALSE: // True or False
			container.innerHTML = `<div class="question-image">
										<img src="/images/no-image.png" class="question-uploaded-image">
									</div>
									<div class="question-content">
										<div class="question">
											<div class="question-number">Question ${questionNo} - True / False</div>
											<textarea name="questions[${questionNo-1}].question"></textarea>
											<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image">
										</div>
										<div class="answers">
											<div>
												<input type="radio" disabled/>
												<div>True</div>
											</div>
											<div>
												<input type="radio" disabled/>
												<div>False</div>
											</div>
										</div>
									</div>							
									<div class="question-settings">
										<img src="/icons/x-square.svg"/>
									</div>`;
			
			break;
		case IDENTIFICATION: // Identification
			container.innerHTML = `<div class="question-image">
										<img src="/images/no-image.png" class="question-uploaded-image">
									</div>
									<div class="question-content">
										<div class="question">
											<div class="question-number">Question ${questionNo} - Identification</div>
											<textarea name="questions[${questionNo-1}].question"></textarea>
											<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image">
										</div>
										<div class="answers">
											<textarea disabled></textarea>
										</div>
									</div>							
									<div class="question-settings">
										<img src="/icons/x-square.svg"/>								
									</div>`;
			break;
		default:
			const errorContainer = document.createElement('div');
		    errorContainer.textContent = 'Error';
		    container = errorContainer;
	}
	
	const fileInputs = container.querySelectorAll('input[type="file"]');
	
	fileInputs.forEach(fileInput => fileInput.addEventListener('change', function(ev){
			
		let imageDisplay;
	        
        if (this.classList.contains('question-input-image')) {
			console.log("Question Image");
            imageDisplay = container.querySelector('.question-uploaded-image');
        } else {
            imageDisplay = null; 
        }
             
        const uploadedFiles = ev.target.files;

        if (uploadedFiles && uploadedFiles[0] && imageDisplay) {

            const file = uploadedFiles[0];
            const reader = new FileReader();

            reader.onload = function (e) {
                imageDisplay.src = e.target.result; 
            };

            reader.readAsDataURL(file); 
        }
	}));
	
	//Show Image When Hovered
	container.querySelector('.question-uploaded-image').addEventListener('mouseover', function(){
		let displayImage = document.querySelector('.display-image');
		displayImage.classList.add('show');
		displayImage.querySelector('img').src = this.src;
	});
	
	//Unshow Image When Not Hovered
	container.querySelector('.question-uploaded-image').addEventListener('mouseout', function(){
		let displayImage = document.querySelector('.display-image');
		displayImage.classList.remove('show');
		displayImage.src = '/images/no-image.png';
	});
	
	//Add Event Listener to the Add Choice of Multiple Choice
	if(questionType == MULTIPLE_CHOICE){
		container.querySelector('.add-option').addEventListener('click', function(){
			
			const answers = document.querySelector(`.answers-${questionNo}`);
			const questionContainer = answers.closest('.question-container');
			const answerCount = questionContainer.getAttribute('answercount');
			
			let answerContainer = document.createElement('div');
			
			answerContainer.innerHTML = `
							<input type="radio" disabled/>
							<input type="text" name="questions[${questionNo-1}].answers[${Number(answerCount)}].answer" value="Option"/>
							<img class="remove-btn" src="/icons/warning.svg"/>`;
						
			answers.appendChild(answerContainer);
			
			questionContainer.setAttribute('answercount', Number(answerCount)+1);
		});
	}
		
	return container;
}

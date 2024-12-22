
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

if(webDto.retrievedQuestions.length != 0){
	let questionCount = 1;
	for(let question of webDto.retrievedQuestions){
		
		let container = createQuestionContainer(questionCount, question.questionType);
		
		let answersContainer = container.querySelector('.answers');
		
		container.querySelector('textarea').textContent = question.question;
		
		if(question.questionImage != null && question.questionImage != ""){
			container.querySelector('.question-uploaded-image').src = `/view/image/${question.id}/${question.questionImage}`;
		}
				
		if(question.questionType == MULTIPLE_CHOICE){
			answersContainer.innerHTML = '';
			if(question.answers != 0){
				let answerCount = 1;
				for(let answer of question.answers){
					console.log("AW");
					let answerContainer = createOption(questionCount, answerCount, answer.isCorrect, answer.answer);
					answersContainer.appendChild(answerContainer);
					answerCount++;	
				}	
				container.setAttribute('answerCount', answerCount-1);
			}
		}
		
		if(question.questionType == TRUE_FALSE){
			if(question.answers[0].isCorrect){
				answersContainer.querySelectorAll('input[type="hidden"]')[0].value = true;
				answersContainer.querySelectorAll('img')[0].src = '/icons/circle-check.svg';
			}else if(question.answers[1].isCorrect){
				answersContainer.querySelectorAll('input[type="hidden"]')[1].value = true;
				answersContainer.querySelectorAll('img')[1].src = '/icons/circle-check.svg';
			}
			
		}
		
		if(question.questionType == IDENTIFICATION){
			answersContainer.querySelectorAll('textarea')[1].textContent = question.answers[0].answer;
		}
		
		contentBodyQuestion.appendChild(container);
		
		questionCount++;
		
		currentTotalQuestion = questionCount;
	}
	totalQuestion.innerHTML = questionCount-1;
}else{
	console.log("NO QUESTIONS");
}


document.querySelector('.add-question-btn').addEventListener('click', function(){
		
	const questionCount = promptQuestionCount.value;
	const questionType = promptQuestionType.value;
	
	totalQuestion.innerHTML = Number(totalQuestion.innerHTML) + Number(questionCount);

	for(let count = 1; count <= Number(questionCount); count++){
		
		let container = createQuestionContainer(currentTotalQuestion, questionType);
		
		contentBodyQuestion.appendChild(container);
		
		currentTotalQuestion++;
	}
});

function createQuestionContainer(questionNo, questionType){
	
	const container = document.createElement('div');
	container.classList.add('question-container');
	container.classList.add(`question-container-${questionNo}`);
	
	switch(questionType){
		case MULTIPLE_CHOICE: // Multiple Choice
			container.setAttribute('questionType', questionType);
			container.setAttribute('answerCount', 1);
			container.innerHTML = `<div class="question-image">
						<img src="/images/no-image.png" class="question-uploaded-image">
					</div>
					<div class="question-content">
						<div class="question">
							<div>Question</div>
							<textarea name="questions[${questionNo-1}].question"></textarea>
							<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
						</div>
						<div class="answers answers-${questionNo}">
												
						</div>
					</div>							
					<div class="question-settings">
						<img src="/icons/x-square.svg" class="remove-question-btn"/>
						<img src="/icons/add-square.svg" class="add-option" />
					</div>`;				
			break;
					
		case TRUE_FALSE: // True or False
			container.innerHTML = `<div class="question-image">
										<img src="/images/no-image.png" class="question-uploaded-image">
									</div>
									<div class="question-content">
										<div class="question">
											<div>Question</div>
											<textarea name="questions[${questionNo-1}].question"></textarea>
											<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
										</div>
										<div class="answers">
											<div>
												<input type="radio" disabled/>
												<div>True</div>
												<img class="correct-btn" src="/icons/circle-check-gray.svg" />
												<input type="hidden" name="questions[${questionNo-1}].answers[0].isCorrect" value="false" />
												<input type="hidden" name="questions[${questionNo-1}].answers[0].answer" value="True" />
											</div>
											<div>
												<input type="radio" disabled/>
												<div>False</div>
												<img class="correct-btn" src="/icons/circle-check-gray.svg" />
												<input type="hidden" name="questions[${questionNo-1}].answers[1].isCorrect" value="false" />
												<input type="hidden" name="questions[${questionNo-1}].answers[1].answer" value="False" />
											</div>
										</div>
									</div>							
									<div class="question-settings">
										<img src="/icons/x-square.svg" class="remove-question-btn"/>
									</div>`;
			
			break;
		case IDENTIFICATION: // Identification
			container.innerHTML = `<div class="question-image">
										<img src="/images/no-image.png" class="question-uploaded-image">
									</div>
									<div class="question-content">
										<div class="question">
											<div>Question</div>
											<textarea name="questions[${questionNo-1}].question"></textarea>
											<input type="file" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
										</div>
										<div class="answers">
											<textarea disabled></textarea>
											<textarea placeholder="Enter correct answer" name="questions[${questionNo-1}].answers[0].answer"></textarea>
										</div>
									</div>							
									<div class="question-settings">
										<img src="/icons/x-square.svg" class="remove-question-btn"/>								
									</div>`;
			break;
		default:
			const errorContainer = document.createElement('div');
		    errorContainer.textContent = 'Error';
		    container = errorContainer;
	}
	
	container.querySelector('.answers').appendChild(createOption(questionNo, 1));
	
	const hiddenQuestionType = document.createElement('input');
    hiddenQuestionType.type = 'hidden';
    hiddenQuestionType.name = `questions[${questionNo-1}].questionType`;
    hiddenQuestionType.value = questionType;
    
    /*
    <div>
								<input type="radio" disabled/>
								<input type="text" name="questions[${questionNo-1}].answers[0].answer" value="Option"/>
								<img class="correct-btn not-delete" src="/icons/circle-check-gray.svg" />
								<input type="hidden" name="questions[${questionNo-1}].answers[0].isCorrect" value="false" />
							</div>	
    */
    
    container.appendChild(hiddenQuestionType);
	
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
								
			answers.appendChild(createOption(questionNo, Number(answerCount)+1));
			
			questionContainer.setAttribute('answercount', Number(answerCount)+1);
		});
	}
	
	container.querySelector('.remove-question-btn').addEventListener('click', function(){
		this.closest('.question-container').remove();
	});
	
	//Add Event Listener to the Check Buttons
	container.querySelectorAll('.correct-btn').forEach(btn => btn.addEventListener('click', function(){
		this.classList.toggle('correct');
		
		const parentElement = this.parentElement;
		
		let hiddenCorrectValue = parentElement.querySelector('input[type="hidden"]');
		
		if(this.classList.contains('correct')){
			this.src = '/icons/circle-check.svg';
			hiddenCorrectValue.value = true;
		}else{
			this.src = '/icons/circle-check-gray.svg';
			hiddenCorrectValue.value = false;
		}
				
	}));
		
	return container;
}

function createOption(questionNo, answerNo, isCorrect=false, answer='Option'){
	let answerContainer = document.createElement('div');
	
	if(isCorrect){
		answerContainer.innerHTML = `
					<input type="radio" disabled/>
					<input type="text" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].answer" value="${answer}"/>
					<img class="correct-btn" src="/icons/circle-check.svg" />
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].isCorrect" value="true"/>`;
	}else{
		answerContainer.innerHTML = `
					<input type="radio" disabled/>
					<input type="text" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].answer" value="${answer}"/>
					<img class="correct-btn" src="/icons/circle-check-gray.svg" />
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].isCorrect" value="false"/>`;
	}
	
	if(answerNo > 1){
		let remove = document.createElement('img');
		remove.classList.add('remove-btn');
		remove.src = '/icons/warning.svg';
		answerContainer.appendChild(remove);	
	}else{
		answerContainer.querySelector('.correct-btn').classList.add('not-delete');
	}
	
	//Add Event Listener to the Check Buttons
	answerContainer.querySelectorAll('.correct-btn').forEach(btn => btn.addEventListener('click', function(){
		this.classList.toggle('correct');
		
		const parentElement = this.parentElement;
		
		let hiddenCorrectValue = parentElement.querySelector('input[type="hidden"]');
		
		if(this.classList.contains('correct')){
			this.src = '/icons/circle-check.svg';
			hiddenCorrectValue.value = true;
		}else{
			this.src = '/icons/circle-check-gray.svg';
			hiddenCorrectValue.value = false;
		}
				
	}));
	
	answerContainer.querySelectorAll('.remove-btn').forEach(btn => btn.addEventListener('click', function(){
		this.parentElement.remove();
		console.log(this);
	}));
			
	return answerContainer;
}

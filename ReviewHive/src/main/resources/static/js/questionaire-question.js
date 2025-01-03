
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
		
		let container = createQuestionContainer(questionCount, question.questionType, question.id);
		
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
					let answerContainer = createOption(questionCount, answerCount, answer.isCorrect, answer.answer, answer.id);
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

document.querySelector('.enable-timer-toggle').addEventListener('change', function() {
    const timerFields = document.querySelectorAll('.timer-field input');

    const isChecked = this.checked;
    timerFields.forEach(input => {
        input.disabled = !isChecked;
    });
});

function createQuestionContainer(questionNo, questionType, questionId=0){
	
	const container = document.createElement('div');
	container.classList.add('question-container');
	container.classList.add(`question-container-${questionNo}`);
	
	container.innerHTML += `
		<input type="hidden" name="questions[${questionNo-1}].questionId" value="${questionId}" />
		<input type="hidden" name="questions[${questionNo-1}].hasModified" value="false" class="hasModified"/>
		<input type="hidden" name="questions[${questionNo-1}].hasDeleted" value="false" class="hasDeleted"/>
		<input type="hidden" name="questions[${questionNo-1}].hasQuestionImageModified" value="false" class="hasQuestionImageModified"/>
		<div class="question-image">
			<img src="/images/no-image.png" style="cursor:pointer;" class="question-uploaded-image">
		</div>`;
	
	switch(questionType){
		case MULTIPLE_CHOICE: // Multiple Choice
			container.setAttribute('questionType', questionType);
			container.setAttribute('answerCount', 1);
			
			container.innerHTML += `
					<div class="question-content">
						<div class="question">
							<div>Question</div>
							<textarea name="questions[${questionNo-1}].question" class="question-textarea"></textarea>
							<input type="file" style="display: none;" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
						</div>
						<div class="answers answers-${questionNo}">
												
						</div>
					</div>							
					<div class="question-settings">
						<img src="/icons/x-square.svg" class="remove-question-btn"/>
						<img src="/icons/add-square.svg" class="add-option" />
					</div>`;		
					
			container.querySelector('.answers').appendChild(createOption(questionNo, 1));
					
			break;
					
		case TRUE_FALSE: // True or False
			container.innerHTML += `
								<div class="question-content">
									<div class="question">
										<div>Question</div>
										<textarea name="questions[${questionNo-1}].question" class="question-textarea"></textarea>
										<input type="file" style="display: none;" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
									</div>
									<div class="answers">
										<div>
											<input type="radio" disabled/>
											<div>True</div>
											<img class="correct-btn" src="/icons/circle-check-gray.svg" />
											<input type="hidden" name="questions[${questionNo-1}].answers[0].isCorrect" value="false" />
											<input type="hidden" name="questions[${questionNo-1}].answers[0].answer" value="True" />
											<input type="hidden" name="questions[${questionNo-1}].answers[0].hasModified" value="false" class="hasModified"/>
											<input type="hidden" name="questions[${questionNo-1}].answers[0].hasDeleted" value="false" class="hasDeleted"/>
										</div>
										<div>
											<input type="radio" disabled/>
											<div>False</div>
											<img class="correct-btn" src="/icons/circle-check-gray.svg" />
											<input type="hidden" name="questions[${questionNo-1}].answers[1].isCorrect" value="false" />
											<input type="hidden" name="questions[${questionNo-1}].answers[1].answer" value="False" />
											<input type="hidden" name="questions[${questionNo-1}].answers[1].hasModified" value="false" class="hasModified"/>
											<input type="hidden" name="questions[${questionNo-1}].answers[1].hasDeleted" value="false" class="hasDeleted"/>
										</div>
									</div>
								</div>							
								<div class="question-settings">
									<img src="/icons/x-square.svg" class="remove-question-btn"/>
								</div>`;
			
			break;
		case IDENTIFICATION: // Identification
			container.innerHTML += `
							<div class="question-content">
								<div class="question">
									<div>Question</div>
									<textarea name="questions[${questionNo-1}].question" class="question-textarea"></textarea>
									<input type="file" style="display: none;" name="questions[${questionNo-1}].questionImage" class="question-input-image" accept=".png, .jpg, .jpeg">
								</div>
								<div class="answers">
									<textarea disabled></textarea>
									<textarea placeholder="Enter correct answer" name="questions[${questionNo-1}].answers[0].answer"></textarea>
									<input type="hidden" name="questions[${questionNo-1}].answers[0].isCorrect" value="true" />
									<input type="hidden" name="questions[${questionNo-1}].answers[0].hasModified" value="false" class="hasModified"/>
									<input type="hidden" name="questions[${questionNo-1}].answers[0].hasDeleted" value="false" class="hasDeleted"/>
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
	
	
	
	const hiddenQuestionType = document.createElement('input');
    hiddenQuestionType.type = 'hidden';
    hiddenQuestionType.name = `questions[${questionNo-1}].questionType`;
    hiddenQuestionType.value = questionType;

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
	
	container.querySelector('.question-textarea').addEventListener('change', function(){
		this.closest('.question-container').querySelector('.hasModified').value = true;
	});
	
	//Show Image When Hovered
	container.querySelector('.question-uploaded-image').addEventListener('mouseover', function(){
		if(!this.src.includes('no-image')){
			let displayImage = document.querySelector('.display-image');
			displayImage.classList.add('show');
			displayImage.querySelector('img').src = this.src;
		}
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
			
			this.closest('.question-container').querySelector('.hasModified').value = true;
		});
	}
	
	container.querySelector('.remove-question-btn').addEventListener('click', function(){
		this.closest('.question-container').style.display = 'none';
		this.closest('.question-container').querySelector('.hasDeleted').value = true;
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

		this.closest('.question-container').querySelector('.hasModified').value = true;
				
	}));
	
	container.querySelector('.question-input-image').addEventListener('change', function(){
		this.closest('.question-container').querySelector('.hasQuestionImageModified').value = true;
	});
	
	container.querySelector('.question-uploaded-image').addEventListener('click', function(){
		this.closest('.question-container').querySelector('.question-input-image').click();
	});
		
	return container;
}

function createOption(questionNo, answerNo, isCorrect=false, answer='Option', answerId = 0){
	let answerContainer = document.createElement('div');
	
	let letter = String.fromCharCode(65 + answerNo-1);
	
	if(isCorrect){
		answerContainer.innerHTML += `
					<label>${letter}.</label>
					<input type="text" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].answer" value="${answer}"/>
					<img class="correct-btn" src="/icons/circle-check.svg" />`;
	}else{
		answerContainer.innerHTML += `
					<label>${letter}.</label>
					<input type="text" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].answer" value="${answer}"/>
					<img class="correct-btn" src="/icons/circle-check-gray.svg" />`;
	}
	
	answerContainer.innerHTML += `
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].isCorrect" value="false"/>
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].answerId" value="${answerId}"/>
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].hasModified" value="false" class="hasModified"/>
					<input type="hidden" name="questions[${questionNo-1}].answers[${Number(answerNo-1)}].hasDeleted" value="false" class="hasDeleted"/>`;
	
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
		
		parentElement.querySelector('.hasModified').value = true;
		this.closest('.question-container').querySelector('.hasModified').value = true;
		
				
	}));
	
	answerContainer.querySelectorAll('.remove-btn').forEach(btn => btn.addEventListener('click', function(){
		this.parentElement.style.display = 'none';
		this.parentElement.querySelector('.hasDeleted').value = true;
		this.parentElement.setAttribute("deleted", "");
		this.closest('.question-container').querySelector('.hasModified').value = true;
		
		
		let answerCount = this.closest('.question-container').getAttribute('answercount');
		this.closest('.question-container').setAttribute('answercount', Number(answerCount) - 1);
		
		let labels = this.closest('.answers').querySelectorAll('label');
		
		let count = 0;
		labels.forEach(label => {
		  if (!label.parentElement.hasAttribute('deleted')) {
			let letter = String.fromCharCode(65 + count);
		    label.innerHTML = letter;
		    count++;
		  }
		});
		
	}));
			
	return answerContainer;
}

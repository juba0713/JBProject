
let hasStart = false;

const START_BUTTON = document.querySelector('.start-btn');

const BUTTONS = document.querySelector('.buttons');

START_BUTTON.addEventListener('click', function(){
	
	addStopButton();
	
	this.remove();
});

function addStopButton(){
	const STOP_BUTTON = document.createElement('button'); 
    STOP_BUTTON.textContent = "STOP";
	STOP_BUTTON.style.background = "#DC3545";
	STOP_BUTTON.classList.add('action-btn');
	
	BUTTONS.innerHTML = '';
		
	BUTTONS.append(STOP_BUTTON);
}

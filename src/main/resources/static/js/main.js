
function executRequest(requestMethod, url, dataToSend, callback) {
		
	fetch(url, {
		  method: requestMethod,
		  headers: {
		    'Content-Type': 'application/json',
		  },
		  body: JSON.stringify(dataToSend),
		})
	.then(response => {
		if(response.status !== 200 && response.status !== 201 && response.status !== 202) {
			response.json().then(data => {showErrorModal(data.message)});
		} else {
			return Promise.resolve(response);
		}})
	.then(response => response.json())
	.then(data => callback(data))	
	.catch(error => showErrorModal(error.message));
}

function showErrorModal(message = '') {

	if (document.getElementById('error-message-modal') != null) {
		document.getElementById('error-message').innerText = message;
		$("#error-message-modal").modal('show');
	} else {
		alert(message);
	}
}

function get(url='', callback) {

	fetch(url)
	.then(response => response.json())
	.then(data => callback(data));
}

function select(element) {
	
	let document = element.ownerDocument;
	const rows = document.getElementsByTagName("tr");
	
	for (let i = 0; i < rows.length; i++) {
         rows[i].classList.remove("font-weight-bold");
         rows[i].classList.remove("selected");
    }        
		
    element.classList.add("font-weight-bold");
    element.classList.add("selected");
}

function parseDateTime(dateTime) {
	
	const date = new Date(dateTime);
	
	let dateString = getDate(date) + ' ' + getTime(date);
	
	return dateString;
}

function getTime(dateTime) {
		
		date = new Date(dateTime);
		let digits = new Intl.NumberFormat("ru-RU",
                { minimumIntegerDigits: 2 });
		return digits.format(date.getHours()) + ":" + digits.format(date.getMinutes());
}
	
function getDate(dateTime) {		
		const date = new Date(dateTime);
		return Intl.DateTimeFormat('ru-RU').format(date)
}

function sortTable(tableId, column) {
	
	let table = this.document.getElementById(tableId);
    	let row = Array.from(table.rows)
    		.slice(1)
    		.sort((rowA, rowB) => rowA.getElementsByTagName('td')[column].innerText > rowB.getElementsByTagName('td')[column].innerText ? 1 : -1);
    	table.tBodies[0].append(...row);
}
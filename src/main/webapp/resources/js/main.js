
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
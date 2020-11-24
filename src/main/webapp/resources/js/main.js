function select(element) {
		
	var document = element.ownerDocument;
	
	for (const element of document.getElementsByTagName("tr")) {
         element.classList.remove("font-weight-bold");
         element.classList.remove("selected");
    }        
		
    element.classList.add("font-weight-bold");
    element.classList.add("selected");
}
function changeClass(elementName, newClassName)
{
	elementName.setAttribute("class", newClassName);
	elementName.setAttribute("className", newClassName);
}
function pre_select(name, val){
	var elements = $A(document.getElementsByName(name));	
	elements.each(
		function test(node){			
			if(node.value==val){
				node.checked = "checked";
			}
		}
	);
}
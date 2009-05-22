/***********************************************************************************
* Utility
***********************************************************************************/
var Utility = new Object();

Utility.getScrollWidth = function()
{
	var xScroll;
	
	if (window.innerHeight && window.scrollMaxY) {	
		xScroll = window.innerWidth + window.scrollMaxX;
	} else if (document.body.scrollWidth > document.body.offsetWidth){ // all but Explorer Mac
		xScroll = document.body.scrollWidth;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		xScroll = document.body.offsetWidth;
	}

    return xScroll;
}

Utility.getScrollHeight = function()
{
	var yScroll;
	
	if (window.innerHeight && window.scrollMaxY) {	
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		yScroll = document.body.offsetHeight;
	}
	
    return yScroll;
}

Utility.getWindowWidth = function()
{
	var windowWidth, windowHeight;
	
	if (self.innerHeight) {	// all except Explorer
		if(document.documentElement.clientWidth){
			windowWidth = document.documentElement.clientWidth; 
		} else {
			windowWidth = self.innerWidth;
		}
	} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
		windowWidth = document.documentElement.clientWidth;
	} else if (document.body) { // other Explorers
		windowWidth = document.body.clientWidth;
	}	
    
    return windowWidth;
}

Utility.getWindowHeight = function()
{
	var windowHeight;

	if (self.innerHeight) {	// all except Explorer
		windowHeight = self.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // other Explorers
		windowHeight = document.body.clientHeight;
	}	
	
	return windowHeight;
}

Utility.getPageWidth = function()
{
    var xScroll = Utility.getScrollWidth();
    var windowWidth = Utility.getWindowWidth();
    return (xScroll > windowWidth) ? xScroll : windowWidth;
}

Utility.getPageHeight = function()
{
    var yScroll = Utility.getScrollHeight();
    var windowHeight = Utility.getWindowHeight();
    return (yScroll > windowHeight) ? yScroll : windowHeight;
}

Utility.getScrollX = function()
{
	var xScroll;

	if (self.pageYOffset) {
		xScroll = self.pageXOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		xScroll = document.documentElement.scrollLeft;
	} else if (document.body) {// all other Explorers
		xScroll = document.body.scrollLeft;	
	}
	
	return xScroll;
}

Utility.getScrollY = function()
{

	var yScroll;

	if (self.pageYOffset) {
		yScroll = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScroll = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScroll = document.body.scrollTop;
	}

	return yScroll;
}

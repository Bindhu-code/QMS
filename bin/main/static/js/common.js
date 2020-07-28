/**
 * 
 */

var rowNum;
window.history.forward();
function noBack() { 
     window.history.forward(); 
}


function pagination(url1){
	var rowNum;
	var pageNo="";
	var perPageSize="";
	var args = ['query'];
	var val1 = window.url(args[0], url1);
	
	if(typeof val1 !== "undefined" && pageNo!=="1")
	{
		//alert((val1.indexOf("pageSize="))+9);
		perPageSize=val1.substring((val1.indexOf("pageSize="))+9, (val1.indexOf("page="))-1);
		pageNo=val1.substring(val1.indexOf("page=")+5, (val1.indexOf("pageName"))-1);
		//alert("here"+perPageSize);
    	rowNum=(pageNo*perPageSize)-perPageSize;
			
	}
	else 
	{
		rowNum=0;	
	}
    
	return rowNum;
	
}
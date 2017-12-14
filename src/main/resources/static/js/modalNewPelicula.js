$(document).on('click', '#boton', function() {

	$(".error").remove();
	
	var y = "https://www.youtube.com/watch?v="
    
    if ( $("#title").val().length == 0) {
    	$("#title").focus().after("<span class='error'> Campo obligatorio </span>");
    	return false;
    }
	
    else if (($("#video").val().lenth == 0) || !($("#video").val().includes(y))) {
    	$("#video").focus().after("<span class='error'> URL válida: </br> https://www.youtube.com/watch?v= </span>");
    	return false;
    }
   
	$("#title").keyup(function(){
    	if($(this).val().length > 0 ){
    		$(".error").fadeOut();
    		return false;
    	}
    });
	
	$("#video").keyup(function(){
    	if($(this).val().length > 0 && $(this).val().includes(y)){
    		$(".error").fadeOut();
    		return false;
    	}
    });
	
});
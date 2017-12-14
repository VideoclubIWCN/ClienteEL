$(document).on('click', '#boton', function() {
	
	$(".error").remove();
	
	var y = "https://www.youtube.com/watch?v=";

    if( $("#plot").val().length == 0 ) {
        $("#plot").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#released").val().length == 0 ) {
        $("#released").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#director").val().length == 0 ) {
        $("#director").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#actors").val().length == 0 ) {
        $("#actors").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#imdbRating").val().length == 0 ) {
        $("#imdbRating").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#runtime").val().length == 0 ) {
        $("#runtime").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#poster").val().length == 0 ) {
        $("#poster").focus().after("<span class='error'> Campo obligatorio </span>");
        return false;
    }
    
    else if( $("#video").val().length == 0 || !($("#video").val().includes(y))) {
        $("#video").focus().after("<span class='error'> URL válida: </br> https://www.youtube.com/watch?v= </span>");
        return false;
    }
    
    $("#plot, #release, #director, #actors, #imdbRating, #runtime, #poster").keyup(function(){
    	if($(this).val().length > 0  && $(this).val() != "N/A"){
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
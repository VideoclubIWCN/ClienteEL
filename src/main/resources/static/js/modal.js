$(document).on('click', '#boton', function() {

	$(".error").remove();
	
	var c = /^[a-zA-Z0-9_\.\/\-]+\@(([a-zA-Z])+\.)+[a-z]+$/;
	
    if( $("#nombre").val().length < 3 || $("#nombre").val().length > 5) {
        $("#nombre").focus().after("<span class='error'> Mín 3, Máx 5 carac </span>");
        return false;
    }
    
    else if( $("#cont").val().length < 3) {
        $("#cont").focus().after("<span class='error'> Mín 3 carac </span>");
        return false;
    }
    
    else if( $("#correo").val() == "" || !c.test($("#correo").val()) ) {
        $("#correo").focus().after("<span class='error'> Email no válido </span>");
        return false;
    }
    
    else if ( !$("#radios input[name='rol']").is(':checked')) {
    	$("#radios").focus().after("<span class='error'> Seleccione un rol </span>");
    	return false;
    }
    
    $("#nombre").keyup(function(){
    	if($(this).val().length >= 3 || $(this).val().length <= 5){
    		$(".error").fadeOut();
    		return false;
    	}
    });
    
    $("#cont").keyup(function(){
    	if($(this).val().length >= 3 ){
    		$(".error").fadeOut();
    		return false;
    	}
    });
    
    
    $("#correo").keyup(function(){
    	if($(this).val() != "" && c.test($(this).val())){
    		$(".error").fadeOut();
    		return false;
    	}
    });
    
    $("#radios input[name='rol']").keyup(function(){
    	if($(this).is(':checked')){
    		$("#error").fadeOut();
    		return false;
    	}
    });
   
});
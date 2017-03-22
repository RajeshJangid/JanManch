/**
 * 
 */
function adjustSize(){
	$('#context-body').css('height', 'auto');
	$('#body-row').css('height', 'auto');
	$('#body-menu').css('height', 'auto');
	$('#body-content').css('height', 'auto');
	var content = $('#body-content').height();
	var menu = $('#body-menu').height();
	if(content>menu){
		$('#context-body').css('height', content);
		$('#body-row').css('height', content);
		$('#body-menu').css('height', content);
	}else{
		$('#context-body').css('height', menu);
		$('#body-row').css('height', menu);
		$('#body-content').css('height', menu);
	}
}

function expandmenu(){
	console.log('expand-menu');
	$('#body-menu').removeClass('menu-collapsed').addClass('menu-expanded');
	$('#collapse-menu').removeClass('hidden');
}

function collapsemenu(){
	console.log('collapse menu');
	$('#body-menu').removeClass('menu-expanded').addClass('menu-collapsed');
	$('#collapse-menu').addClass('hidden');
}

$(document).ready(function(){
	adjustSize();
	$(window).resize(adjustSize);
	$('#expand-menu').on('click', function(){
		expandmenu();
	});
	
	$('#collapse-menu').on('click', function(){
		collapsemenu();
	});
});
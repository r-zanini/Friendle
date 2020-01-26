$(document).scroll(function(){
	let $navBarSelector = $('#navBar');
	$navBarSelector.toggleClass('scrolled', $(this).scrollTop() > $navBarSelector.height());
});
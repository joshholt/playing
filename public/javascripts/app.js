$(function() {
	$('.ajax-post-link').click(function (evt) {
		evt.preventDefault();
		$.post($(evt.target).data('url'), function() {
			window.location.reload();
		});
	});
});
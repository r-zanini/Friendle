$(function () {
	var calendarEl = document.getElementById('calendar');

	function getCalendarHeight() {return Math.max(document.documentElement.clientHeight, window.innerHeight || 150) - 150;}
	$(window).on('resize', function (e) {calendar.setOption('height', getCalendarHeight())});


	var calendar = new FullCalendar.Calendar(calendarEl, {
		plugins: ['bootstrap', 'dayGrid', 'timeGrid', 'list'],
		header: {
			left: 'title',
			center: 'dayGridMonth,timeGridWeek,timeGridDay',
			right: 'prev,today,next'
		},
		height: getCalendarHeight(),
		themeSystem: 'bootstrap',
		defaultView: 'dayGridMonth',
		firstDay: 0,
		events: '/schedule/json',
		eventTimeFormat: { // like '14:30'
			hour: '2-digit',
			minute: '2-digit',
			meridiem: false
		},
		eventMouseEnter: function (info) {$(info.el).popover('show')},
		eventMouseLeave: function (info) {$(info.el).popover('hide')}
	});
	calendar.render();
});
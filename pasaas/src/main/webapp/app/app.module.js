(function() {
	'use strict';

	angular
		.module('pasaasApp', [
			'ngStorage',
			'tmh.dynamicLocale',
			'pascalprecht.translate',
			'ngResource',
			'ngCookies',
			'ngAria',
			'ngCacheBuster',
			'ngFileUpload',
			'ui.bootstrap',
			'ui.bootstrap.datetimepicker',
			'ui.router',
			'infinite-scroll',
			'angularMoment',
			// jhipster-needle-angularjs-add-module JHipster will add new module here
			'angular-loading-bar',


			// 'ui.router',                    // Routing
			// 'ui.calendar',                  // Calendar
			// 'ui.bootstrap',                 // Bootstrap
			// 'ui.checkbox',                  // Custom checkbox
			// 'ui.knob',                      // Knob input
			// 'ui.switchery',                 // iOS7 swich style
			// 'angular-peity',                // Peity charts
			// 'easypiechart',                 // Easy pie charts
			// 'angular-flot',                 // Flot charts
			// 'angular-rickshaw',             // Rickshaw carts
			// 'summernote',                   // Text editor
			// 'nouislider',                   // Slider
			// 'datePicker',                   // Datapicker
			'datatables',                   // Dynamic tables
			// 'localytics.directives',        // Chosen select
			// 'angles',                       // Charts js
			// 'ui.map',                       // Google maps
			// 'ngGrid',                       // ngGrid
			// 'ui.codemirror',                // Code editor
			// 'ui.tree'                       // Nestable list
		])
		.run(run);
		
	run.$inject = ['stateHandler', 'translationHandler'];

	function run(stateHandler, translationHandler) {
		stateHandler.initialize();
		translationHandler.initialize();
	}
})();

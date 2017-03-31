(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf12', ['$scope', controller_pdf12])
;
function controller_pdf12($scope){

	$scope.page_data1 = {
		"name" : "Sudarshan Shenvi",
		"subscription_type" : "Silver",
		"address" : "Vijaynagar, Banglore",
		"text_about" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitat.",
		"count_incedent" : "36",
		"count_solved" : "26",
		"count_monitoring" : "6",
	}

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Profile",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Platform",
				"class" : "",
				"is_active" : false
			},
			
			{
				"link" : "/",
				"label" : " Profile",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();

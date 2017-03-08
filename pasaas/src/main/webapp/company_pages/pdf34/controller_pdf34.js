(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf34', ['$scope',controller_pdf34])
;
function controller_pdf34($scope){

	$scope.page_data1 = {
		"alert_title" : "Successfully Sent.",
		"alert_text" : "You have sent 2 invitations, invitation will be sent to their correspondent email address to verify their account.",
	}

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Organization",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},			
			{
				"link" : "/",
				"label" : "Organization",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();

(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf36', ['$scope', controller_pdf36])
;
function controller_pdf36($scope){

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "BDA",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},	
			{
				"link" : "index.html",
				"label" : "Platform",
				"class" : "",
				"is_active" : false
			},			
			{
				"link" : "/",
				"label" : "BDA",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();

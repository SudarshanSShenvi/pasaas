(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf47', ['$scope',controller_pdf47])
;
function controller_pdf47($scope){
	$scope.new_configuration = {};
	
	$scope.save_form = function(coming_object){
		alert(JSON.stringify(coming_object));
	};

	$scope.list_status = [
        {
            "back_color" : "Green",
            "front_color" : "white",
            "label" : "Active"
        },{
            "back_color" : "Gray",
            "front_color" : "white",
            "label" : "Inactive"
        },{
            "back_color" : "Red",
            "front_color" : "white",
            "label" : "Deleted"
        }
    ];

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "BDA Configurations",
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
				"label" : "BDA",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Configuration",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Reliability Configuration - Create",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();

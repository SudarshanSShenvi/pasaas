(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf39', ['$scope', controller_pdf39])
;
function controller_pdf39($scope){
	$scope.new_score = {};
	$scope.form_disabled = true;

	$scope.view_form = function(){
		$scope.form_disabled = true;
	};
	$scope.edit_form = function(){
		$scope.form_disabled = false;
	};
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
		"page_header_title" : "BDA Alarms",
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
				"label" : " Alarms  ",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : " Alarms RC - View",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();
(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf11', ['$scope', 'service_file_list', controller_pdf11])
;
function controller_pdf11($scope, service_file_list){

	$scope.page_data1 = {
		"folder_list" : [
			"Project Files",
			"Data Load",
			"Results",
			"Testing",
			"Upgrades",
		],
		"tag_list" : [
			"Family",
			"Work",
			"Home",
			"Children",
			"Holidays",
			"Music",
			"Photography",
			"Film",
		]
	}

	$scope.get_file_list = function(){
		service_file_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.file_list = response.data;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_file_list();

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "File Manager",
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
				"label" : " File Manager ",
				"class" : "active",
				"is_active" : true
			}
		]

	};

}

})();

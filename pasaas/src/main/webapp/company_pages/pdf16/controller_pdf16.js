(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf16', ['$scope', 'service_project_list',controller_pdf16])
;
function controller_pdf16($scope, service_project_list){

	$scope.get_project_list = function(){
		service_project_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.project_list = response.data;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_project_list();

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Project List",
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
				"label" : "Project List",
				"class" : "active",
				"is_active" : true
			}
		]

	};

}

})();

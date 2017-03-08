(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf31', ['$scope', 'service_api_users',controller_pdf31])
;
function controller_pdf31($scope, service_api_users){
	$scope.get_users_list = function(){
		service_api_users.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.users_list = response.data;
			}, 
			function on_error(response){
				console.log("ON ERROR: " + response.statusText);
			}
		);
	};
	$scope.get_users_list();

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

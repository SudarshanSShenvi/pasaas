(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf46', ['$scope', 'service_bda_reliablility_config_list', controller_pdf46])
;
function controller_pdf46($scope, service_bda_reliablility_config_list){

	$scope.checkAll = function (coming_array) {
        if ($scope.selectedAll) {
            $scope.selectedAll = false;
        } else {
            $scope.selectedAll = true;
        }
        angular.forEach(coming_array, function (item) {
            item.Selected = $scope.selectedAll;
        });

    };

	$scope.get_bda_reliablility_config_list = function(){
		service_bda_reliablility_config_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.bda_reliablility_config_list = response.data;
		    	$scope.array_for_icheckguru = $scope.bda_reliablility_config_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_bda_reliablility_config_list();

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
				"label" : "Reliability Configuration - List",
				"class" : "active",
				"is_active" : true
			}
		]

	};


}

})();

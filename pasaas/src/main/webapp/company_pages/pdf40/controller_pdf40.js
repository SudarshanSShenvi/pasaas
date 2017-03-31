(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf40', ['$scope', 'service_bda_network_element_list', controller_pdf40])
;
function controller_pdf40($scope, service_bda_network_element_list){

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
	
	$scope.get_bda_network_element_list = function(){
		service_bda_network_element_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.bda_network_element_list = response.data;
		    	$scope.array_for_icheckguru = $scope.bda_network_element_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_bda_network_element_list();

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
				"label" : "Networks",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Network Elements - List",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();

(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf53', ['$scope', 'service_prediction_reliablity_list', controller_pdf53])
;
function controller_pdf53($scope, service_prediction_reliablity_list){

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

	$scope.get_prediction_reliablity_list = function(){
		service_prediction_reliablity_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.prediction_reliablity_list = response.data;
		    	$scope.array_for_icheckguru = $scope.prediction_reliablity_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_prediction_reliablity_list();

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "BDA Core",
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
				"label" : "Core",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : " Prediction Reliability - List",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();

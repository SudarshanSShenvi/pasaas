(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf55', ['$scope', 'service_reliability_score_list', controller_pdf55])
;
function controller_pdf55($scope, service_reliability_score_list){

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

	$scope.get_reliability_score_list = function(){
		service_reliability_score_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.reliability_score_list = response.data;
		    	$scope.array_for_icheckguru = $scope.reliability_score_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_reliability_score_list();

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
				"label" : " Reliability Score - List",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();

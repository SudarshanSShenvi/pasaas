(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf20', ['$scope', 'service_group_list',controller_pdf20])
;
function controller_pdf20($scope, service_group_list){

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

	$scope.get_group_list = function(){
		service_group_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.group_list = response.data;
				$scope.array_for_icheckguru = $scope.group_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_group_list();

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Groups",
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
				"label" : "Permissions",
				"class" : "active",
				"is_active" : true
			}
		]

	};

}

})();

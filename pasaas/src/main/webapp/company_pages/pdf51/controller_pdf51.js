(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf51', ['$scope', 'service_email_delivery_failed_list', controller_pdf51])
;
function controller_pdf51($scope, service_email_delivery_failed_list){

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

	$scope.get_email_delivery_failed_list = function(){
		service_email_delivery_failed_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.email_delivery_failed_list = response.data;
		    	$scope.array_for_icheckguru = $scope.email_delivery_failed_list;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_email_delivery_failed_list();

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
				"label" : "Notifications",
				"class" : "",
				"is_active" : false
			},
			{		
				"link" : "/",
				"label" : " Email delivery failed- List",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();

(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf18', ['$scope', controller_pdf18])
;
function controller_pdf18($scope){
	$scope.list_group = [
		{
			"status" : "Active",
			"name" : "NOC Admin",
			"total_members" : "12"
		}
		,{
			"status" : "Active",
			"name" : "NOC User",
			"total_members" : "24"
		}
		,{
			"status" : "Inactive",
			"name" : "NOC Manager",
			"total_members" : "2"
		}
		,{
			"status" : "Active",
			"name" : "NOC Admin",
			"total_members" : "5"
		}
	];

	$scope.array_for_icheckguru = $scope.list_group;

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
				"label" : "Groups",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();

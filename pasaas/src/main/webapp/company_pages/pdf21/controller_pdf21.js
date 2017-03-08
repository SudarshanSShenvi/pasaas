(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf21', ['$scope', controller_pdf21])
;
function controller_pdf21($scope){
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
	$scope.list_functionality = [
		{
			"name" : "Create Delete and Modify Application Group"
		},
		{
			"name" : "Create View and Modify Group"
		},
		{
			"name" : "View Group"
		},
		{
			"name" : "Pull Reports of Group"
		}
	];
	$scope.array_for_icheckguru = $scope.list_functionality;
	
	$scope.list_user = [
		{
			"name" : "Sudarshan Shenvi",
			"project_name" : "-N/M-",
			"user_status" : "active",
			"member_type": "Silver"
		}
		,{
			"name" : "Sandeep Sheshappa",
			"project_name" : "-N/M-",
			"user_status" : "active",
			"member_type": "Silver"
		}
		,{
			"name" : "Avinash Ambale",
			"project_name" : "-N/M-",
			"user_status" : "inactive",
			"member_type": "Silver"
		}
		,{
			"name" : "Thomas Reddick",
			"project_name" : "-N/M-",
			"user_status" : "active",
			"member_type": "Silver"
		}
	];

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
				"class" : "",
				"is_active" : false
			},
			
			{
				"link" : "/",
				"label" : " Create Permissions",
				"class" : "active",
				"is_active" : true
			}
		]

	};

	
	
}

})();

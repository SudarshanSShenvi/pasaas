(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf19', ['$scope', controller_pdf19])
;
function controller_pdf19($scope){
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
	$scope.array_for_icheckguru = $scope.list_user;
	
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
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Create Group",
				"class" : "active",
				"is_active" : true
			}
		]

	};

	$scope.send = function(user){
		debugger;
		alert('gnam====' + user.email);
		alert('description==' +user.description);
		alert('parent:===' +user.password);
	};
	
}

})();

(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_users', ['$scope', '$rootScope',controller_users])
;
function controller_users($scope, $rootScope){

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
			"avatar" : "img/a1.jpg",
			"member_type": "Silver"
		}
		,{
			"name" : "Sandeep Sheshappa",
			"project_name" : "-N/M-",
			"user_status" : "active",
			"avatar" : "img/a2.jpg",
			"member_type": "Silver"
		}
		,{
			"name" : "Avinash Ambale",
			"project_name" : "-N/M-",
			"user_status" : "inactive",
			"avatar" : "img/a3.jpg",
			"member_type": "Silver"
		}
		,{
			"name" : "Thomas Reddick",
			"project_name" : "-N/M-",
			"user_status" : "active",
			"avatar" : "img/a4.jpg",
			"member_type": "Silver"
		}
	];

	$rootScope.progress_now = 40;

	$scope.all_users = [
		{
			name: "",
			project: "Project Name",
			user_status: "Active",
			member_type: "Silver",
		},
		{
			name: "",
			project: "-N/M-",
			user_status: "Active",
			member_type: "Silver",
		},
		{
			name: "",
			project: "Project Name",
			user_status: "Active",
			member_type: "Silver",
		},
		{
			name: "",
			project: "-N/M-",
			user_status: "Active",
			member_type: "Silver",
		},
	];

}

})();

(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_files', ['$scope', '$rootScope', 'service_file_list',controller_files])
;
function controller_files($scope, $rootScope, service_file_list){
	$scope.get_file_list = function(){
		service_file_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.file_list = response.data;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_file_list();

	$rootScope.progress_now = 60;

}

})();

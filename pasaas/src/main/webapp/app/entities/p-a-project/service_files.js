(function () {	'use strict';

angular
.module('pasaasApp')
.service('service_files', ['$http', service_files])
;

function service_files($http){
	var service = this;
	
	service.fetch_full_list = function(){
		return $http.get('api/readfilelist/user/pervazive/Pervazive/Telco Project/ppa-repo/traindata');
	};

}


})();
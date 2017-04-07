(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAPredictionScore', PAPredictionScore);

    PAPredictionScore.$inject = ['$resource', 'DateUtils'];

    function PAPredictionScore ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-prediction-scores/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdon = DateUtils.convertDateTimeFromServer(data.createdon);
                        data.date = DateUtils.convertLocalDateFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date = DateUtils.convertLocalDateToServer(copy.date);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.date = DateUtils.convertLocalDateToServer(copy.date);
                    return angular.toJson(copy);
                }
            }
        });
    }
    
    angular    
    .module('pasaasApp')
    .factory('PAPredictionScoreC1', PAPredictionScoreC1); 
    
    PAPredictionScoreC1.$inject = ['$resource', 'DateUtils']; function PAPredictionScoreC1 ($resource, DateUtils) {
    	var resourceUrl =  'api/p-a-prediction-scores/:id';     
    	return $resource(resourceUrl, {}, { 
    		'query': { method: 'GET', isArray: true}
    	}); }
    
    angular    
    .module('pasaasApp')
    .factory('PAPredictionScoreSu', PAPredictionScoreSu); 
    
    PAPredictionScoreSu.$inject = ['$resource', 'DateUtils']; function PAPredictionScoreSu ($resource, DateUtils) {
    	var resourceUrl =  'api/p-a-prediction-scores/suops/:projectId';     
    	return $resource(resourceUrl, {}, { 
    		'query': { method: 'GET', isArray: true}
    	}); }
    
    angular    
    .module('pasaasApp')
    .factory('PAPredictionScoreChartB', PAPredictionScoreChartB); 
    
    PAPredictionScoreChartB.$inject = ['$resource', 'DateUtils']; function PAPredictionScoreChartB ($resource, DateUtils) {
    	var resourceUrl =  'api/p-a-prediction-scores/chartb/:projectId';     
    	return $resource(resourceUrl, {}, { 
    		'query': { method: 'GET', isArray: true}
    	}); }
    
    angular    
    .module('pasaasApp')
    .factory('PAPredictionScoreChartC', PAPredictionScoreChartC); 
    
    PAPredictionScoreChartC.$inject = ['$resource', 'DateUtils']; function PAPredictionScoreChartC ($resource, DateUtils) {
    	var resourceUrl =  'api/p-a-prediction-scores/chartc/:projectId';     
    	return $resource(resourceUrl, {}, { 
    		'query': { method: 'GET', isArray: true}
    	}); }
    
    angular    
    .module('pasaasApp')
    .factory('PAPredictionScorePieChart', PAPredictionScorePieChart); 
    
    PAPredictionScorePieChart.$inject = ['$resource', 'DateUtils']; function PAPredictionScorePieChart ($resource, DateUtils) {
    	var resourceUrl =  'api/p-a-prediction-scores/distribution/:projectId';     
    	return $resource(resourceUrl, {}, { 
    		'query': { method: 'GET', isArray: true}
    	}); }
    	
})();

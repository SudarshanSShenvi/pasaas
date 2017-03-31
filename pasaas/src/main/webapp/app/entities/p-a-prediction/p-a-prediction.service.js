(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAPrediction', PAPrediction);

    PAPrediction.$inject = ['$resource', 'DateUtils'];

    function PAPrediction ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-predictions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.predictiondate = DateUtils.convertDateTimeFromServer(data.predictiondate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAAccPrecision', PAAccPrecision);

    PAAccPrecision.$inject = ['$resource', 'DateUtils'];

    function PAAccPrecision ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-acc-precisions/:id';

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

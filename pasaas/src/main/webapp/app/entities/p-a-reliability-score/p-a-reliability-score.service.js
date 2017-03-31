(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAReliabilityScore', PAReliabilityScore);

    PAReliabilityScore.$inject = ['$resource', 'DateUtils'];

    function PAReliabilityScore ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-reliability-scores/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

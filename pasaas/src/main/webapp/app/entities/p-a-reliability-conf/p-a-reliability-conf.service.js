(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAReliabilityConf', PAReliabilityConf);

    PAReliabilityConf.$inject = ['$resource'];

    function PAReliabilityConf ($resource) {
        var resourceUrl =  'api/p-a-reliability-confs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

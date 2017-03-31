(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PADataConnector', PADataConnector);

    PADataConnector.$inject = ['$resource'];

    function PADataConnector ($resource) {
        var resourceUrl =  'api/p-a-data-connectors/:id';

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

(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAPMTRequest', PAPMTRequest);

    PAPMTRequest.$inject = ['$resource'];

    function PAPMTRequest ($resource) {
        var resourceUrl =  'api/p-apmt-requests/:id';

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

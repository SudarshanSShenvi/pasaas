(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PASaxCode', PASaxCode);

    PASaxCode.$inject = ['$resource'];

    function PASaxCode ($resource) {
        var resourceUrl =  'api/p-a-sax-codes/:id';

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

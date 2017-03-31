(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAReport', PAReport);

    PAReport.$inject = ['$resource'];

    function PAReport ($resource) {
        var resourceUrl =  'api/p-a-reports/:id';

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

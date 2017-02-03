(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAGeneralConfig', PAGeneralConfig);

    PAGeneralConfig.$inject = ['$resource'];

    function PAGeneralConfig ($resource) {
        var resourceUrl =  'api/p-a-general-configs/:id';

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

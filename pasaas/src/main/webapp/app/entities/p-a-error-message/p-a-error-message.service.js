(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAErrorMessage', PAErrorMessage);

    PAErrorMessage.$inject = ['$resource', 'DateUtils'];

    function PAErrorMessage ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-error-messages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.errortime = DateUtils.convertDateTimeFromServer(data.errortime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

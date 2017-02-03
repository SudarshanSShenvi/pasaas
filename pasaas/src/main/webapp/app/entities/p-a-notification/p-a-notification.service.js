(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PANotification', PANotification);

    PANotification.$inject = ['$resource', 'DateUtils'];

    function PANotification ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-notifications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.msgtouchtime = DateUtils.convertDateTimeFromServer(data.msgtouchtime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

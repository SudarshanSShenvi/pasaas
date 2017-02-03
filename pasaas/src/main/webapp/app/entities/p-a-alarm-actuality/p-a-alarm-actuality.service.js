(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAAlarmActuality', PAAlarmActuality);

    PAAlarmActuality.$inject = ['$resource', 'DateUtils'];

    function PAAlarmActuality ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-alarm-actualities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.occured = DateUtils.convertDateTimeFromServer(data.occured);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PARawAlarmData', PARawAlarmData);

    PARawAlarmData.$inject = ['$resource', 'DateUtils'];

    function PARawAlarmData ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-raw-alarm-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.starteddate = DateUtils.convertDateTimeFromServer(data.starteddate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

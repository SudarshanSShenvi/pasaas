(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAAlarmRCA', PAAlarmRCA);

    PAAlarmRCA.$inject = ['$resource'];

    function PAAlarmRCA ($resource) {
        var resourceUrl =  'api/p-a-alarm-rcas/:id';

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

(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PASchedulerInterval', PASchedulerInterval);

    PASchedulerInterval.$inject = ['$resource'];

    function PASchedulerInterval ($resource) {
        var resourceUrl =  'api/p-a-scheduler-intervals/:id';

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

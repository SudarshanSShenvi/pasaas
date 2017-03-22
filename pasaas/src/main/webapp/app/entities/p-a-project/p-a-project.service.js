(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAProject', PAProject);

    PAProject.$inject = ['$resource'];

    function PAProject ($resource) {
        var resourceUrl =  'api/p-a-projects/:id';

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
    
    angular
    .module('pasaasApp')
    .factory('PAProject', PAProject);

PAProject.$inject = ['$resource'];

function PAProject ($resource) {
    var resourceUrl =  'api/p-a-projects/suops';

    return $resource(resourceUrl, {}, {
        'query': { method: 'GET', isArray: true},
    });
}
})();

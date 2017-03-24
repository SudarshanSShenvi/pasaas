(function () {
    'use strict';

    angular
        .module('pasaasApp')
        .factory('User', User);

    User.$inject = ['$resource'];

    function User ($resource) {
        var service = $resource('api/users/:login', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'save': { method:'POST' },
            'update': { method:'PUT' },
            'delete':{ method:'DELETE'}
        });

        return service;
    }
    
    angular
    .module('pasaasApp')
    .factory('User1', User1);

User1.$inject = ['$resource'];

function User1 ($resource) {
    var service = $resource('api/users/suops', {}, {
        'query': {method: 'GET', isArray: true},
        'update': { method:'PUT' }
    });

    return service;
}
})();

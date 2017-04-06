(function() {
    'use strict';
    angular
        .module('imgcheckApp')
        .factory('Issues', Issues);

    Issues.$inject = ['$resource'];

    function Issues ($resource) {
        var resourceUrl =  'api/issues/:id';

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

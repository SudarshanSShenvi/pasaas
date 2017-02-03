(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAFileUpload', PAFileUpload);

    PAFileUpload.$inject = ['$resource'];

    function PAFileUpload ($resource) {
        var resourceUrl =  'api/p-a-file-uploads/:id';

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

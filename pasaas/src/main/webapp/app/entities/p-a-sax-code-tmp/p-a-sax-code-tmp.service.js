(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PASaxCodeTmp', PASaxCodeTmp);

    PASaxCodeTmp.$inject = ['$resource'];

    function PASaxCodeTmp ($resource) {
        var resourceUrl =  'api/p-a-sax-code-tmps/:id';

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
    .factory('PASaxCodeTmpSu', PASaxCodeTmpSu);

    PASaxCodeTmpSu.$inject = ['$resource'];

function PASaxCodeTmpSu ($resource) {
    var resourceUrl =  'api/p-a-sax-code-tmps/suops/:projectId';

    return $resource(resourceUrl, {}, {
        'query': { method: 'GET', isArray: true}
    });
}
})();

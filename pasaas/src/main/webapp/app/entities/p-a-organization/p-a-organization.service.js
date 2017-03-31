(function() {
    'use strict';
    angular
        .module('pasaasApp')
        .factory('PAOrganization', PAOrganization);

    PAOrganization.$inject = ['$resource', 'DateUtils'];

    function PAOrganization ($resource, DateUtils) {
        var resourceUrl =  'api/p-a-organizations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.validfrom = DateUtils.convertDateTimeFromServer(data.validfrom);
                        data.validto = DateUtils.convertDateTimeFromServer(data.validto);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
    
    
    angular
    .module('pasaasApp')
    .factory('PAOrganization1', PAOrganization1);

PAOrganization1.$inject = ['$resource', 'DateUtils'];

function PAOrganization1 ($resource, DateUtils) {
    var resourceUrl =  'api/p-a-organizations/name/:organizationName';

    return $resource(resourceUrl, {}, {
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                if (data) {
                    data = angular.fromJson(data);
                    data.validfrom = DateUtils.convertDateTimeFromServer(data.validfrom);
                    data.validto = DateUtils.convertDateTimeFromServer(data.validto);
                }
                return data;
            }
        }
    });
}

    
 angular
    .module('pasaasApp')
    .factory('PAOrganizationSu', PAOrganizationSu);

 PAOrganizationSu.$inject = ['$resource', 'DateUtils'];

function PAOrganizationSu ($resource, DateUtils) {
    var resourceUrl =  'api/p-a-organizations/suops';

    return $resource(resourceUrl, {}, {
        'query': { method: 'GET', isArray: true}
    });
}
})();

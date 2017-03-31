(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeDetailController', PASaxCodeDetailController);

    PASaxCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PASaxCode', 'PAOrganization', 'PAProject'];

    function PASaxCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, PASaxCode, PAOrganization, PAProject) {
        var vm = this;

        vm.pASaxCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pASaxCodeUpdate', function(event, result) {
            vm.pASaxCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

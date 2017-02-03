(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeTmpDetailController', PASaxCodeTmpDetailController);

    PASaxCodeTmpDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PASaxCodeTmp', 'PAOrganization', 'PAProject'];

    function PASaxCodeTmpDetailController($scope, $rootScope, $stateParams, previousState, entity, PASaxCodeTmp, PAOrganization, PAProject) {
        var vm = this;

        vm.pASaxCodeTmp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pASaxCodeTmpUpdate', function(event, result) {
            vm.pASaxCodeTmp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

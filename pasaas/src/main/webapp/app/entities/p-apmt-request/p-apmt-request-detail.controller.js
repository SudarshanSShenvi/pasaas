(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPMTRequestDetailController', PAPMTRequestDetailController);

    PAPMTRequestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAPMTRequest', 'PAOrganization', 'PAProject'];

    function PAPMTRequestDetailController($scope, $rootScope, $stateParams, previousState, entity, PAPMTRequest, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPMTRequest = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAPMTRequestUpdate', function(event, result) {
            vm.pAPMTRequest = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

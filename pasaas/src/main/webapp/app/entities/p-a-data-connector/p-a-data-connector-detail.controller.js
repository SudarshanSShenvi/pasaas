(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PADataConnectorDetailController', PADataConnectorDetailController);

    PADataConnectorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PADataConnector', 'PAOrganization', 'PAProject'];

    function PADataConnectorDetailController($scope, $rootScope, $stateParams, previousState, entity, PADataConnector, PAOrganization, PAProject) {
        var vm = this;

        vm.pADataConnector = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pADataConnectorUpdate', function(event, result) {
            vm.pADataConnector = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

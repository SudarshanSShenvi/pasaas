(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANEDetailsDetailController', PANEDetailsDetailController);

    PANEDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PANEDetails', 'PAOrganization', 'PAProject'];

    function PANEDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, PANEDetails, PAOrganization, PAProject) {
        var vm = this;

        vm.pANEDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pANEDetailsUpdate', function(event, result) {
            vm.pANEDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAErrorMessageDetailController', PAErrorMessageDetailController);

    PAErrorMessageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAErrorMessage', 'PAOrganization', 'PAProject'];

    function PAErrorMessageDetailController($scope, $rootScope, $stateParams, previousState, entity, PAErrorMessage, PAOrganization, PAProject) {
        var vm = this;

        vm.pAErrorMessage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAErrorMessageUpdate', function(event, result) {
            vm.pAErrorMessage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionDetailController', PAPredictionDetailController);

    PAPredictionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAPrediction', 'PAOrganization', 'PAProject'];

    function PAPredictionDetailController($scope, $rootScope, $stateParams, previousState, entity, PAPrediction, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPrediction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAPredictionUpdate', function(event, result) {
            vm.pAPrediction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

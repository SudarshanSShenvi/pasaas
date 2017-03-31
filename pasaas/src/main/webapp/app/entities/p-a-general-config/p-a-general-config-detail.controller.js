(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAGeneralConfigDetailController', PAGeneralConfigDetailController);

    PAGeneralConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAGeneralConfig'];

    function PAGeneralConfigDetailController($scope, $rootScope, $stateParams, previousState, entity, PAGeneralConfig) {
        var vm = this;

        vm.pAGeneralConfig = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAGeneralConfigUpdate', function(event, result) {
            vm.pAGeneralConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAccPrecisionDetailController', PAAccPrecisionDetailController);

    PAAccPrecisionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAAccPrecision', 'PAProject', 'PAOrganization'];

    function PAAccPrecisionDetailController($scope, $rootScope, $stateParams, previousState, entity, PAAccPrecision, PAProject, PAOrganization) {
        var vm = this;

        vm.pAAccPrecision = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAAccPrecisionUpdate', function(event, result) {
            vm.pAAccPrecision = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

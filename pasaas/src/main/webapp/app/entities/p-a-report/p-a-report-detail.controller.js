(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportDetailController', PAReportDetailController);

    PAReportDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAReport', 'PAOrganization'];

    function PAReportDetailController($scope, $rootScope, $stateParams, previousState, entity, PAReport, PAOrganization) {
        var vm = this;

        vm.pAReport = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAReportUpdate', function(event, result) {
            vm.pAReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

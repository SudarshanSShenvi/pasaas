(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportDetailController', PAReportDetailController);

    PAReportDetailController.$inject = ['DataUtils', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAReport', 'PAOrganization'];

    function PAReportDetailController(DataUtils, $scope, $rootScope, $stateParams, previousState, entity, PAReport, PAOrganization) {
        var vm = this;

        vm.pAReport = entity;
        vm.previousState = previousState.name;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('pasaasApp:pAReportUpdate', function(event, result) {
            vm.pAReport = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportDeleteController',PAReportDeleteController);

    PAReportDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAReport'];

    function PAReportDeleteController($uibModalInstance, entity, PAReport) {
        var vm = this;

        vm.pAReport = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAReport.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

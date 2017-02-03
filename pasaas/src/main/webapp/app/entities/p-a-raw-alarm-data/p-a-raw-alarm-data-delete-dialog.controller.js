(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PARawAlarmDataDeleteController',PARawAlarmDataDeleteController);

    PARawAlarmDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'PARawAlarmData'];

    function PARawAlarmDataDeleteController($uibModalInstance, entity, PARawAlarmData) {
        var vm = this;

        vm.pARawAlarmData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PARawAlarmData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

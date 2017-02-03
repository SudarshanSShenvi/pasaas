(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmActualityDeleteController',PAAlarmActualityDeleteController);

    PAAlarmActualityDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAAlarmActuality'];

    function PAAlarmActualityDeleteController($uibModalInstance, entity, PAAlarmActuality) {
        var vm = this;

        vm.pAAlarmActuality = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAAlarmActuality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

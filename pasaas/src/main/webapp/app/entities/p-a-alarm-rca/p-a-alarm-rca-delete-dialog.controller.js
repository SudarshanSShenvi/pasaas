(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmRCADeleteController',PAAlarmRCADeleteController);

    PAAlarmRCADeleteController.$inject = ['$uibModalInstance', 'entity', 'PAAlarmRCA'];

    function PAAlarmRCADeleteController($uibModalInstance, entity, PAAlarmRCA) {
        var vm = this;

        vm.pAAlarmRCA = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAAlarmRCA.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerIntervalDeleteController',PASchedulerIntervalDeleteController);

    PASchedulerIntervalDeleteController.$inject = ['$uibModalInstance', 'entity', 'PASchedulerInterval'];

    function PASchedulerIntervalDeleteController($uibModalInstance, entity, PASchedulerInterval) {
        var vm = this;

        vm.pASchedulerInterval = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PASchedulerInterval.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

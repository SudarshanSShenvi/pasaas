(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerDeleteController',PASchedulerDeleteController);

    PASchedulerDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAScheduler'];

    function PASchedulerDeleteController($uibModalInstance, entity, PAScheduler) {
        var vm = this;

        vm.pAScheduler = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAScheduler.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

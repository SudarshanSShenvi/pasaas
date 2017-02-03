(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PABusinessPlanDeleteController',PABusinessPlanDeleteController);

    PABusinessPlanDeleteController.$inject = ['$uibModalInstance', 'entity', 'PABusinessPlan'];

    function PABusinessPlanDeleteController($uibModalInstance, entity, PABusinessPlan) {
        var vm = this;

        vm.pABusinessPlan = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PABusinessPlan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

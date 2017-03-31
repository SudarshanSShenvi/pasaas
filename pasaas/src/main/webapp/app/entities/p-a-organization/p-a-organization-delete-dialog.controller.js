(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDeleteController',PAOrganizationDeleteController);

    PAOrganizationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAOrganization'];

    function PAOrganizationDeleteController($uibModalInstance, entity, PAOrganization) {
        var vm = this;

        vm.pAOrganization = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAOrganization.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

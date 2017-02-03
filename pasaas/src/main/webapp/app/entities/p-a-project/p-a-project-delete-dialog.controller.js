(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAProjectDeleteController',PAProjectDeleteController);

    PAProjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAProject'];

    function PAProjectDeleteController($uibModalInstance, entity, PAProject) {
        var vm = this;

        vm.pAProject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAProject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

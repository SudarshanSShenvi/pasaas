(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANEDetailsDeleteController',PANEDetailsDeleteController);

    PANEDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'PANEDetails'];

    function PANEDetailsDeleteController($uibModalInstance, entity, PANEDetails) {
        var vm = this;

        vm.pANEDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PANEDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

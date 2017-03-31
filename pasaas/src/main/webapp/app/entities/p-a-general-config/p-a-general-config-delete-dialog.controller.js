(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAGeneralConfigDeleteController',PAGeneralConfigDeleteController);

    PAGeneralConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAGeneralConfig'];

    function PAGeneralConfigDeleteController($uibModalInstance, entity, PAGeneralConfig) {
        var vm = this;

        vm.pAGeneralConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAGeneralConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

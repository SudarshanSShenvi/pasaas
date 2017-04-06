(function() {
    'use strict';

    angular
        .module('imgcheckApp')
        .controller('IssuesDeleteController',IssuesDeleteController);

    IssuesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Issues'];

    function IssuesDeleteController($uibModalInstance, entity, Issues) {
        var vm = this;

        vm.issues = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Issues.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityConfDialogController', PAReliabilityConfDialogController);

    PAReliabilityConfDialogController.$inject = ['$state', 'previousState', '$timeout', '$scope', '$stateParams', 'entity', 'PAReliabilityConf', 'PAOrganization', 'PAProject'];

    function PAReliabilityConfDialogController ($state, previousState, $timeout, $scope, $stateParams, entity, PAReliabilityConf, PAOrganization, PAProject) {
        var vm = this;

        vm.pAReliabilityConf = entity;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();
        vm.paprojects = PAProject.query();

        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.pAReliabilityConf.id !== null) {
                PAReliabilityConf.update(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
            } else {
                PAReliabilityConf.save(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            swal({
                title: "Success!",
                text: "Record Edited Successfully",
                type: "success"
            });
            $scope.$emit('pasaasApp:pAReliabilityConfUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            swal({
                title: "Error!",
                text: "Record Edit Failed",
                type: "error"
            });
            vm.isSaving = false;
        }


    }
})();


// (function() {
//     'use strict';

//     angular
//         .module('pasaasApp')
//         .controller('PAReliabilityConfDialogController', PAReliabilityConfDialogController);

//     PAReliabilityConfDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAReliabilityConf', 'PAOrganization', 'PAProject'];

//     function PAReliabilityConfDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAReliabilityConf, PAOrganization, PAProject) {
//         var vm = this;

//         vm.pAReliabilityConf = entity;
//         vm.clear = clear;
//         vm.save = save;
//         vm.paorganizations = PAOrganization.query();
//         vm.paprojects = PAProject.query();

//         $timeout(function (){
//             angular.element('.form-group:eq(1)>input').focus();
//         });

//         function clear () {
//             $uibModalInstance.dismiss('cancel');
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.pAReliabilityConf.id !== null) {
//                 PAReliabilityConf.update(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
//             } else {
//                 PAReliabilityConf.save(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
//             }
//         }

//         function onSaveSuccess (result) {
//             $scope.$emit('pasaasApp:pAReliabilityConfUpdate', result);
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }


//     }
// })();

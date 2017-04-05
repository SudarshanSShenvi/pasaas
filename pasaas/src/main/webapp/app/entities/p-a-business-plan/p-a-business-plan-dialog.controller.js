(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PABusinessPlanDialogController', PABusinessPlanDialogController);

    PABusinessPlanDialogController.$inject = ['$state', 'previousState', '$timeout', '$scope', '$stateParams', 'entity', 'PABusinessPlan', 'PAOrganization'];

    function PABusinessPlanDialogController ($state, previousState, $timeout, $scope, $stateParams, entity, PABusinessPlan, PAOrganization) {
        var vm = this;

        vm.pABusinessPlan = entity;
        // vm.clear = clear;
        vm.save = save;
        // [Commented 4/4/2017 - Calling organization while opening Business plan in new/edit mode]
        // vm.paorganizations = PAOrganization.query(); 

        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.pABusinessPlan.id !== null) {
                PABusinessPlan.update(vm.pABusinessPlan, onSaveSuccess, onSaveError);
            } else {
                PABusinessPlan.save(vm.pABusinessPlan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            swal({
                title: "Success!",
                text: "Done!",
                type: "success"
            });
            $scope.$emit('pasaasApp:pABusinessPlanUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState, {}, {reload: true});
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
//         .controller('PABusinessPlanDialogController', PABusinessPlanDialogController);

//     PABusinessPlanDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PABusinessPlan', 'PAOrganization'];

//     function PABusinessPlanDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PABusinessPlan, PAOrganization) {
//         var vm = this;

//         vm.pABusinessPlan = entity;
//         vm.clear = clear;
//         vm.save = save;
//         vm.paorganizations = PAOrganization.query();

//         $timeout(function (){
//             angular.element('.form-group:eq(1)>input').focus();
//         });

//         function clear () {
//             $uibModalInstance.dismiss('cancel');
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.pABusinessPlan.id !== null) {
//                 PABusinessPlan.update(vm.pABusinessPlan, onSaveSuccess, onSaveError);
//             } else {
//                 PABusinessPlan.save(vm.pABusinessPlan, onSaveSuccess, onSaveError);
//             }
//         }

//         function onSaveSuccess (result) {
//             $scope.$emit('pasaasApp:pABusinessPlanUpdate', result);
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }


//     }
// })();

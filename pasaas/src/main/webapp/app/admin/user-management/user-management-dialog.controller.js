(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$state', 'previousState', '$stateParams', 'entity', 'User', 'JhiLanguageService', 'DataUtils', '$scope'];

    function UserManagementDialogController ($state, previousState, $stateParams, entity, User, JhiLanguageService, DataUtils, $scope) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN', 'ROLE_MANAGER'];
        //TODO dynamic referencing for organization. project and authorities
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        vm.previousState = previousState.name;
        
        JhiLanguageService.getAll().then(function (languages) {
            vm.languages = languages;
        });

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess1, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess2, onSaveError);
            }
        }
        
        function onSaveSuccess1 (result) {
            swal({
                title: "Success!",
                text: "Record Edited Successfully",
                type: "success"
            });

            vm.isSaving = false;
            if(vm.previousState === 'user-management.su'){
                $state.go(vm.previousState);
            }else{
                $state.go('p-a-organization.name',{organizationName : previousState.prev_state_param});
            }
        }

        function onSaveSuccess2 (result) {
            swal({
                title: "Success!",
                text: "Record Edited Successfully",
                type: "success"
            });

            vm.isSaving = false;
            if(vm.previousState === 'user-management.su'){
                $state.go(vm.previousState);
            }else{
                $state.go('p-a-organization.name',{organizationName : vm.pAOrganization.organization});
            }
        }

        function onSaveError () {
            swal({
                title: "Error!",
                text: "Record Edit Failed",
                type: "error"
            });
            vm.isSaving = false;
        }

        vm.setImage = function ($file, user) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        user.image = base64Data;
                        user.imageContentType = $file.type;
                    });
                });
            }
        };
    }
})();


// (function() {
//     'use strict';

//     angular
//         .module('pasaasApp')
//         .controller('UserManagementDialogController',UserManagementDialogController);

//     UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', 'JhiLanguageService', 'DataUtils', '$scope'];

//     function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, JhiLanguageService, DataUtils, $scope) {
//         var vm = this;

//         vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN', 'ROLE_MANAGER'];
//         //TODO dynamic referencing for organization. project and authorities
//         vm.clear = clear;
//         vm.languages = null;
//         vm.save = save;
//         vm.user = entity;
//         vm.byteSize = DataUtils.byteSize;
//         vm.openFile = DataUtils.openFile;
        
//         JhiLanguageService.getAll().then(function (languages) {
//             vm.languages = languages;
//         });
        
//         function clear () {
//             $uibModalInstance.dismiss('cancel');
//         }

//         function onSaveSuccess (result) {
//             vm.isSaving = false;
//             $uibModalInstance.close(result);
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.user.id !== null) {
//                 User.update(vm.user, onSaveSuccess, onSaveError);
//             } else {
//                 User.save(vm.user, onSaveSuccess, onSaveError);
//             }
//         }
        
//         vm.setImage = function ($file, user) {
//             if ($file && $file.$error === 'pattern') {
//                 return;
//             }
//             if ($file) {
//                 DataUtils.toBase64($file, function(base64Data) {
//                     $scope.$apply(function() {
//                         user.image = base64Data;
//                         user.imageContentType = $file.type;
//                     });
//                 });
//             }
//         };
//     }
// })();

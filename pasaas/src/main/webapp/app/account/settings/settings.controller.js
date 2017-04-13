(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('SettingsController', SettingsController);

    SettingsController.$inject = ['$scope', 'Principal', 'Auth', 'JhiLanguageService', '$translate', 'DataUtils'];

    function SettingsController ($scope, Principal, Auth, JhiLanguageService, $translate, DataUtils) {
        $scope.page_data1 = {
            "name" : "Sudarshan Shenvi",
            "subscription_type" : "Silver",
            "address" : "Vijaynagar, Banglore",
            "text_about" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitat.",
            "count_incedent" : "36",
            "count_solved" : "26",
            "count_monitoring" : "6",
        }

        $scope.page_meta_data = {
            "has_header" : true,
            "page_header_title" : "Profile",
            // "breadcrumb_data" : [
            //     {
            //         "link" : "index.html",
            //         "label" : "Home",
            //         "class" : "",
            //         "is_active" : false
            //     },
            //     {
            //         "link" : "/",
            //         "label" : "Platform",
            //         "class" : "",
            //         "is_active" : false
            //     },
                
            //     {
            //         "link" : "/",
            //         "label" : " Profile",
            //         "class" : "active",
            //         "is_active" : true
            //     }
            // ]

        };
        var vm = this;

        vm.error = null;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;

        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            console.log(account);
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login,
                image: account.image,
                imageContentType: account.imageContentType,
                organizations: account.organizations
            };
        };

        // $scope.my_acc = account;

        Principal.identity().then(function(account) {
            $scope.my_acc = account;
        });
        
        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });

        function save () {
            console.log(vm.settingsAccount);
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {
                    console.log(account);
                    vm.settingsAccount = copyAccount(account);
                });
                JhiLanguageService.getCurrent().then(function(current) {
                    if (vm.settingsAccount.langKey !== current) {
                        $translate.use(vm.settingsAccount.langKey);
                    }
                });
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }

        
        vm.setImage = function ($file, user) {
            // console.log(JSON.stringify(user), null, 4);
            console.log(user);
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

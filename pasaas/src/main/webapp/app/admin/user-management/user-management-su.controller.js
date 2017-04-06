(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('UserManagementController', UserManagementController);

    UserManagementController.$inject = ['Principal', 'User1', 'ParseLinks', 'AlertService', '$state', 'pagingParams', 'paginationConstants', 'JhiLanguageService', 'DataUtils'];

    function UserManagementController(Principal, User1, ParseLinks, AlertService, $state, pagingParams, paginationConstants, JhiLanguageService, DataUtils) {
        var vm = this;

        vm.users = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            User1.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                //hide anonymous user from user management: it's a required user for Spring Security
                var hiddenUsersSize = 0;
                for (var i in data) {
                    if (data[i]['login'] === 'anonymoususer') {
                        data.splice(i, 1);
                        hiddenUsersSize++;
                    }
                }

                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
                for (var i = 0; i < data.length; i++) {
                    vm.users.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.users = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();

// (function() {
//     'use strict';

//     angular
//         .module('pasaasApp')
//         .controller('UserManagementController', UserManagementController);

//     UserManagementController.$inject = ['Principal', 'User1', 'ParseLinks', 'AlertService', '$state', 'pagingParams', 'paginationConstants', 'JhiLanguageService', 'DataUtils'];

//     function UserManagementController(Principal, User1, ParseLinks, AlertService, $state, pagingParams, paginationConstants, JhiLanguageService, DataUtils) {
//         var vm = this;

//         vm.authorities = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPERADMIN'];
//         //TODO dynamic referencing for organization. project and authorities
//         vm.currentAccount = null;
//         vm.languages = null;
//         vm.loadAll = loadAll;
//         vm.setActive = setActive;
//         vm.users = [];
//         vm.page = 1;
//         vm.totalItems = null;
//         vm.clear = clear;
//         vm.links = null;
//         vm.loadPage = loadPage;
//         vm.predicate = pagingParams.predicate;
//         vm.reverse = pagingParams.ascending;
//         vm.itemsPerPage = paginationConstants.itemsPerPage;
//         vm.transition = transition;
//         vm.openFile = DataUtils.openFile;
//         vm.byteSize = DataUtils.byteSize;

//         vm.loadAll();
        
        
//         JhiLanguageService.getAll().then(function (languages) {
//             vm.languages = languages;
//         });
//         Principal.identity().then(function(account) {
//             vm.currentAccount = account;
//         });

//         function setActive (user, isActivated) {
//             user.activated = isActivated;
//             User1.update(user, function () {
//                 vm.loadAll();
//                 vm.clear();
//             });
//         }

//         function loadAll () {
//             User1.query({
//                 page: pagingParams.page - 1,
//                 size: vm.itemsPerPage,
//                 sort: sort()
//             }, onSuccess, onError);
//         }

//         function onSuccess(data, headers) {
//             //hide anonymous user from user management: it's a required user for Spring Security
//             var hiddenUsersSize = 0;
//             for (var i in data) {
//                 if (data[i]['login'] === 'anonymoususer') {
//                     data.splice(i, 1);
//                     hiddenUsersSize++;
//                 }
//             }
//             vm.links = ParseLinks.parse(headers('link'));
//             vm.totalItems = headers('X-Total-Count') - hiddenUsersSize;
//             vm.queryCount = vm.totalItems;
//             vm.page = pagingParams.page;
//             vm.users = data;
//         }

//         function onError(error) {
//             AlertService.error(error.data.message);
//         }

//         function clear () {
//             vm.user = {
//                 id: null, login: null, firstName: null, lastName: null, email: null,
//                 activated: null, langKey: null, createdBy: null, createdDate: null,
//                 lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
//                 resetKey: null, authorities: null
//             };
//         }

//         function sort () {
//             var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
//             if (vm.predicate !== 'id') {
//                 result.push('id');
//             }
//             return result;
//         }

//         function loadPage (page) {
//             vm.page = page;
//             vm.transition();
//         }

//         function transition () {
//             $state.transitionTo($state.$current, {
//                 page: vm.page,
//                 sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
//                 search: vm.currentSearch
//             });
//         }
//     }
// })();

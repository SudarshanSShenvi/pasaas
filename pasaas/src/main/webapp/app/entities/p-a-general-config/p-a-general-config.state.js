(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('p-a-general-config', {
            parent: 'entity',
            url: '/p-a-general-config',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAGeneralConfig.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-configs.html',
                    controller: 'PAGeneralConfigController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAGeneralConfig');
                    $translatePartialLoader.addPart('pAStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('p-a-general-config-detail', {
            parent: 'entity',
            url: '/p-a-general-config/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'pasaasApp.pAGeneralConfig.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-config-detail.html',
                    controller: 'PAGeneralConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pAGeneralConfig');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PAGeneralConfig', function($stateParams, PAGeneralConfig) {
                    return PAGeneralConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-general-config',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('p-a-general-config-detail.edit', {
            parent: 'p-a-general-config-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-config-dialog.html',
                    controller: 'PAGeneralConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAGeneralConfig', function(PAGeneralConfig) {
                            return PAGeneralConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-general-config.new', {
            parent: 'p-a-general-config',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-config-dialog.html',
                    controller: 'PAGeneralConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                hdfsurl: null,
                                sparkurl: null,
                                dr_script: null,
                                dr_inseriesformat: null,
                                dr_outseriesformat: null,
                                dr_inputfile: null,
                                dr_expressionfile: null,
                                dr_outputfile: null,
                                dr_seriesgcolindex: null,
                                dr_seriesstart: null,
                                dr_seriesend: null,
                                dr_seriesnxt: null,
                                df_inputfile: null,
                                df_outputfile: null,
                                df_entityfld: null,
                                df_seriesfld: null,
                                df_inseriesformat: null,
                                df_outseriesformat: null,
                                df_isheader: null,
                                df_script: null,
                                df_skipfldindexes: null,
                                ss_inputfile: null,
                                ss_outputfile: null,
                                ss_saxcodefldindex: null,
                                ss_subseqinterval: null,
                                ss_subseqintervalthreshhold: null,
                                ss_script: null,
                                ss_tempopfile: null,
                                ss_inputdirname: null,
                                sq_ipaddr: null,
                                sq_mysqlpwd: null,
                                sq_mysqldb: null,
                                sq_loadlocalinfile: null,
                                sq_daydumppath: null,
                                sq_updquery: null,
                                sq_insertquery: null,
                                sq_script: null,
                                sq_command: null,
                                sq_localinfiile: null,
                                pastatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('p-a-general-config', null, { reload: 'p-a-general-config' });
                }, function() {
                    $state.go('p-a-general-config');
                });
            }]
        })
        .state('p-a-general-config.edit', {
            parent: 'p-a-general-config',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-config-dialog.html',
                    controller: 'PAGeneralConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PAGeneralConfig', function(PAGeneralConfig) {
                            return PAGeneralConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-general-config', null, { reload: 'p-a-general-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('p-a-general-config.delete', {
            parent: 'p-a-general-config',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/p-a-general-config/p-a-general-config-delete-dialog.html',
                    controller: 'PAGeneralConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PAGeneralConfig', function(PAGeneralConfig) {
                            return PAGeneralConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('p-a-general-config', null, { reload: 'p-a-general-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

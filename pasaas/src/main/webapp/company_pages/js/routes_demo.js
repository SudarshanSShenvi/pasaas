function routes_demo($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/company_pages/create_new_user");
    $stateProvider
    .state('company_pages', {
        abstract: true,
        parent: 'app',
        url: "/company_pages",
        templateUrl: "views/common/content.html",
    })
    // .state('dashboards', {
    //     abstract: true,
    //     url: "/dashboards",
    //     templateUrl: "views/common/content.html",
    // })
    // .state('dashboards.dashboard_1', {
    //     url: "/dashboard_1",
    //     templateUrl: "views/dashboard_1.html",
    // })
    
   
    .state('company_pages.pdf1', {
        url: '/pdf1',
        templateUrl: 'company_pages/pdf1/pdf1.ng.html',
        controller: 'controller_pdf1',
        data: { pageTitle: 'pdf1' }
    })
    .state('company_pages.pdf2', {
        url: '/pdf2',
        templateUrl: 'company_pages/pdf2/pdf2.ng.html',
        controller: 'controller_pdf2',
        data: { pageTitle: 'pdf2' }
    })
    .state('company_pages.pdf3', {
        url: '/pdf3',
        templateUrl: 'company_pages/pdf3/pdf3.ng.html',
        controller: 'controller_pdf3',
        data: { pageTitle: 'pdf3' }
    })
    .state('company_pages.pdf4', {
        url: '/pdf4',
        templateUrl: 'company_pages/pdf4/pdf4.ng.html',
        controller: 'controller_pdf4',
        data: { pageTitle: 'pdf4' }
    })
    .state('company_pages.pdf5', {
        url: '/pdf5',
        templateUrl: 'company_pages/pdf5/pdf5.ng.html',
        controller: 'controller_pdf5',
        data: { pageTitle: 'pdf5' }
    })
    .state('company_pages.pdf6', {
        url: '/pdf6',
        templateUrl: 'company_pages/pdf6/pdf6.ng.html',
        controller: 'controller_pdf6',
        data: { pageTitle: 'pdf6' }
    })
    .state('company_pages.pdf10', {
        url: '/pdf10',
        templateUrl: 'company_pages/pdf10/pdf10.ng.html',
        controller: 'controller_pdf10',
        data: { pageTitle: 'pdf10' }
    })
    .state('company_pages.pdf11', {
        url: '/pdf11',
        templateUrl: 'company_pages/pdf11/pdf11.ng.html',
        controller: 'controller_pdf11',
        data: { pageTitle: 'pdf11' }
    })
    .state('company_pages.pdf12', {
        url: '/pdf12',
        templateUrl: 'company_pages/pdf12/pdf12.ng.html',
        controller: 'controller_pdf12',
        data: { pageTitle: 'pdf12' }
    })
    .state('company_pages.pdf13', {
        url: '/pdf13',
        templateUrl: 'company_pages/pdf13/pdf13.ng.html',
        controller: 'controller_pdf13',
        data: { pageTitle: 'pdf13' }
    })
    .state('company_pages.pdf13.project_details', {
        url: '/project_details',
        templateUrl: 'company_pages/pdf13/project_details/project_details.ng.html',
        controller: 'controller_project_details',
        data: { pageTitle: 'project_details' }
    })
    .state('company_pages.pdf13.users', {
        url: '/users',
        templateUrl: 'company_pages/pdf13/users/users.ng.html',
        controller: 'controller_users',
        data: { pageTitle: 'users' }
    })
    .state('company_pages.pdf13.files', {
        url: '/files',
        templateUrl: 'company_pages/pdf13/files/files.ng.html',
        controller: 'controller_files',
        data: { pageTitle: 'files' }
    })
    .state('company_pages.pdf16', {
        url: '/pdf16',
        templateUrl: 'company_pages/pdf16/pdf16.ng.html',
        controller: 'controller_pdf16',
        data: { pageTitle: 'pdf16' }
    })
    .state('company_pages.pdf17', {
        url: '/pdf17',
        templateUrl: 'company_pages/pdf17/pdf17.ng.html',
        controller: 'controller_pdf17',
        data: { pageTitle: 'pdf17' }
    })
    .state('company_pages.pdf18', {
        url: '/pdf18',
        templateUrl: 'company_pages/pdf18/pdf18.ng.html',
        controller: 'controller_pdf18',
        data: { pageTitle: 'pdf18' }
    })
    .state('company_pages.pdf19', {
        url: '/pdf19',
        templateUrl: 'company_pages/pdf19/pdf19.ng.html',
        controller: 'controller_pdf19',
        data: { pageTitle: 'pdf19' }
    })
    .state('company_pages.pdf21', {
        url: '/pdf21',
        templateUrl: 'company_pages/pdf21/pdf21.ng.html',
        controller: 'controller_pdf21',
        data: { pageTitle: 'pdf21' }
    })
    .state('company_pages.pdf26', {
        url: '/pdf26',
        templateUrl: 'company_pages/pdf26/pdf26.ng.html',
        controller: 'controller_pdf26',
        data: { pageTitle: 'pdf26' }
    })
    .state('company_pages.pdf36', {
        url: '/pdf36',
        templateUrl: 'company_pages/pdf36/pdf36.ng.html',
        controller: 'controller_pdf36',
        data: { pageTitle: 'pdf36' }
    })
    .state('company_pages.pdf37', {
        url: '/pdf37',
        templateUrl: 'company_pages/pdf37/pdf37.ng.html',
        controller: 'controller_pdf37',
        data: { pageTitle: 'pdf37' }
    })
    .state('company_pages.pdf39', {
        url: '/pdf39',
        templateUrl: 'company_pages/pdf39/pdf39.ng.html',
        controller: 'controller_pdf39',
        data: { pageTitle: 'pdf39' }
    })
    .state('company_pages.pdf38', {
        url: '/pdf38',
        templateUrl: 'company_pages/pdf38/pdf38.ng.html',
        controller: 'controller_pdf38',
        data: { pageTitle: 'pdf38' }
    })
    .state('company_pages.pdf40', {
        url: '/pdf40',
        templateUrl: 'company_pages/pdf40/pdf40.ng.html',
        controller: 'controller_pdf40',
        data: { pageTitle: 'pdf40' }
    })
    .state('company_pages.pdf43', {
        url: '/pdf43',
        templateUrl: 'company_pages/pdf43/pdf43.ng.html',
        controller: 'controller_pdf43',
        data: { pageTitle: 'pdf43' }
    })
    .state('company_pages.pdf46', {
        url: '/pdf46',
        templateUrl: 'company_pages/pdf46/pdf46.ng.html',
        controller: 'controller_pdf46',
        data: { pageTitle: 'pdf46' }
    })
    .state('company_pages.pdf49', {
        url: '/pdf49',
        templateUrl: 'company_pages/pdf49/pdf49.ng.html',
        controller: 'controller_pdf49',
        data: { pageTitle: 'pdf49' }
    })
    .state('company_pages.pdf51', {
        url: '/pdf51',
        templateUrl: 'company_pages/pdf51/pdf51.ng.html',
        controller: 'controller_pdf51',
        data: { pageTitle: 'pdf51' }
    })
    .state('company_pages.pdf53', {
        url: '/pdf53',
        templateUrl: 'company_pages/pdf53/pdf53.ng.html',
        controller: 'controller_pdf53',
        data: { pageTitle: 'pdf53' }
    })
    .state('company_pages.pdf55', {
        url: '/pdf55',
        templateUrl: 'company_pages/pdf55/pdf55.ng.html',
        controller: 'controller_pdf55',
        data: { pageTitle: 'pdf55' }
    })
    .state('company_pages.pdf57', {
        url: '/pdf57',
        templateUrl: 'company_pages/pdf57/pdf57.ng.html',
        controller: 'controller_pdf57',
        data: { pageTitle: 'pdf57' }
    })
    .state('company_pages.pdf59', {
        url: '/pdf59',
        templateUrl: 'company_pages/pdf59/pdf59.ng.html',
        controller: 'controller_pdf59',
        data: { pageTitle: 'pdf59' }
    })
    .state('company_pages.pdf20', {
        url: '/pdf20',
        templateUrl: 'company_pages/pdf20/pdf20.ng.html',
        controller: 'controller_pdf20',
        data: { pageTitle: 'pdf20' }
    })
    .state('company_pages.pdf60', {
        url: '/pdf60',
        templateUrl: 'company_pages/pdf60/pdf60.ng.html',
        controller: 'controller_pdf60',
        data: { pageTitle: 'pdf60' }
    })
    .state('company_pages.pdf58', {
        url: '/pdf58',
        templateUrl: 'company_pages/pdf58/pdf58.ng.html',
        controller: 'controller_pdf58',
        data: { pageTitle: 'pdf58' }
    })
    .state('company_pages.pdf56', {
        url: '/pdf56',
        templateUrl: 'company_pages/pdf56/pdf56.ng.html',
        controller: 'controller_pdf56',
        data: { pageTitle: 'pdf56' }
    })
    .state('company_pages.pdf54', {
        url: '/pdf54',
        templateUrl: 'company_pages/pdf54/pdf54.ng.html',
        controller: 'controller_pdf54',
        data: { pageTitle: 'pdf54' }
    })
    .state('company_pages.pdf52', {
        url: '/pdf52',
        templateUrl: 'company_pages/pdf52/pdf52.ng.html',
        controller: 'controller_pdf52',
        data: { pageTitle: 'pdf52' }
    })
    .state('company_pages.pdf50', {
        url: '/pdf50',
        templateUrl: 'company_pages/pdf50/pdf50.ng.html',
        controller: 'controller_pdf50',
        data: { pageTitle: 'pdf50' }
    })
    .state('company_pages.pdf48', {
        url: '/pdf48',
        templateUrl: 'company_pages/pdf48/pdf48.ng.html',
        controller: 'controller_pdf48',
        data: { pageTitle: 'chapter30' }
    })
    .state('company_pages.pdf47', {
        url: '/pdf47',
        templateUrl: 'company_pages/pdf47/pdf47.ng.html',
        controller: 'controller_pdf47',
        data: { pageTitle: 'chapter31' }
    })
    .state('company_pages.pdf45', {
        url: '/pdf45',
        templateUrl: 'company_pages/pdf45/pdf45.ng.html',
        controller: 'controller_pdf45',
        data: { pageTitle: 'pdf45' }
    })
    .state('company_pages.pdf44', {
        url: '/pdf44',
        templateUrl: 'company_pages/pdf44/pdf44.ng.html',
        controller: 'controller_pdf44',
        data: { pageTitle: 'pdf44' }
    })
    .state('company_pages.pdf42', {
        url: '/pdf42',
        templateUrl: 'company_pages/pdf42/pdf42.ng.html',
        controller: 'controller_pdf42',
        data: { pageTitle: 'pdf42' }
    })
    .state('company_pages.pdf41', {
        url: '/pdf41',
        templateUrl: 'company_pages/pdf41/pdf41.ng.html',
        controller: 'controller_pdf41',
        data: { pageTitle: 'pdf41' }
    })
    .state('company_pages.pdf31', {
        url: '/pdf31',
        templateUrl: 'company_pages/pdf31/pdf31.ng.html',
        controller: 'controller_pdf31',
        data: { pageTitle: 'pdf31' }
    })
    .state('company_pages.pdf28', {
        url: '/pdf28',
        templateUrl: 'company_pages/pdf28/pdf28.ng.html',
        controller: 'controller_pdf28',
        data: { pageTitle: 'pdf28' }
    })
    .state('company_pages.pdf23', {
        url: '/pdf23',
        templateUrl: 'company_pages/pdf23/pdf23.ng.html',
        controller: 'controller_pdf23',
        data: { pageTitle: 'pdf23' }
    })
    .state('company_pages.pdf23.step_one', {
        url: '/step_one',
        templateUrl: 'company_pages/step_one/step_one.ng.html',
        controller: 'controller_step_one',
        data: { pageTitle: 'step_one' }
    })
    .state('company_pages.pdf23.step_two', {
        url: '/step_two',
        templateUrl: 'company_pages/step_two/step_two.ng.html',
        controller: 'controller_step_two',
        data: { pageTitle: 'step_two' }
    })
    .state('company_pages.pdf23.step_three', {
        url: '/step_three',
        templateUrl: 'company_pages/step_three/step_three.ng.html',
        controller: 'controller_step_three',
        data: { pageTitle: 'step_three' }
    })  
    .state('company_pages.pdf29', {
        url: '/pdf29',
        templateUrl: 'company_pages/pdf29/pdf29.ng.html',
        controller: 'controller_pdf29',
        data: { pageTitle: 'pdf29' }
    })
    .state('company_pages.pdf30', {
        url: '/pdf30',
        templateUrl: 'company_pages/pdf30/pdf30.ng.html',
        controller: 'controller_pdf30',
        data: { pageTitle: 'pdf30' }
    })
    .state('company_pages.pdf34', {
        url: '/pdf34',
        templateUrl: 'company_pages/pdf34/pdf34.ng.html',
        controller: 'controller_pdf34',
        data: { pageTitle: 'pdf34' }
    })
    .state('company_pages.create_new_user', {
        url: '/create_new_user',
        templateUrl: 'company_pages/create_new_user/create_new_user.ng.html',
        controller: 'controller_create_new_user',
        data: { pageTitle: 'create_new_user' }
    })

    ;
}
angular
    .module('pasaasApp')
    .config(routes_demo)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });
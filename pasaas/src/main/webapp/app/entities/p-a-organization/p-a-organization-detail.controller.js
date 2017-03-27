(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDetailController', PAOrganizationDetailController);

    PAOrganizationDetailController.$inject = ['$http', '$state', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

    function PAOrganizationDetailController($http, $state, $scope, $rootScope, $stateParams, previousState, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) 
    {
        var vm = this;

        vm.pAOrganization = entity;
        vm.previousState = previousState.name;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.datePickerOpenStatus.validfrom = false;
        vm.datePickerOpenStatus.validto = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.calling_new_project = calling_new_project; 

        function cb_sessionVarsSuccess (result) {
            console.log("Inside cb_sessionVarsSuccess : " + result.data.message);
        }

        function cb_sessionVarsError () {
            console.log("Inside cb_sessionVarsError : " + "Error Occoured");
            swal({
                title: "Error!",
                text: "Error Occoured while calling sessionVars",
                type: "error"
            });
        }

        vm.call_to_sessionVars = function(){
            // swal("Inside sessionVars call");
            // var data_sessionVars_url = "http://10.10.10.176:8088/api/triggersessionVarsOps/Telco%20Project";
            var data_sessionVars_url = "api/sessionVars/" + vm.pAOrganization.organization;
            $http.post(data_sessionVars_url).then(cb_sessionVarsSuccess, cb_sessionVarsError);
        }
        vm.call_to_sessionVars();

        function calling_new_project(){
            alert("Inside calling_new_project");
            $rootScope.this_organization = vm.pAOrganization.organization;
            $state.go('p-a-project.new');
        }

        var unsubscribe = $rootScope.$on('pasaasApp:pAOrganizationUpdate', function(event, result) {
            vm.pAOrganization = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        vm.user_slider = {
            min: 0,
            max: vm.pAOrganization.pabporg.users,
            from:vm.pAOrganization.pausers.length,
            type: 'single',
            prefix: "",
            maxPostfix: "",
            prettify: false,
            hasGrid: false
        };
        vm.project_slider = {
            min: 0,
            max: vm.pAOrganization.pabporg.projects,
            from:vm.pAOrganization.paprojects.length,
            type: 'single',
            prefix: "",
            maxPostfix: "",
            prettify: false,
            hasGrid: false
        };
    }
})();

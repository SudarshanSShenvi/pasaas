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

		// let x = function(){
		//     console.log();
		// }
		// let y = function(callback){
		//     console.log();
		//     callback();
		// }

		// y(coming_user, x);
		vm.delete_project = function(coming_project_id){
			swal({
				title: "Are you sure?",
				// text: "You will not be able to recover this file!",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "Yes, remove it!",
				cancelButtonText: "No, cancel please!",
				closeOnConfirm: false,
				closeOnCancel: false
			},
			function(isConfirm){
				if (isConfirm) {
					// console.log(coming_project_id);
					PAProject.delete({id: coming_project_id},
		                function () {
		                	$state.reload();
		                    // $uibModalInstance.close(true);
							swal("Deleted", "Deleted Suucessfully!", "success");
		                });
					// p-a-project.delete({id:each_project.id})
					// actual_removal(coming_user, save_organization);
				} else {
					swal("Cancelled", "Delete Operation Canceled", "error");
				}
			});
		}

		vm.remove_user = function(coming_user){
			swal({
				title: "Are you sure?",
				// text: "You will not be able to recover this file!",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "Yes, remove it!",
				cancelButtonText: "No, cancel please!",
				closeOnConfirm: false,
				closeOnCancel: false
			},
			function(isConfirm){
				if (isConfirm) {
					actual_removal(coming_user, save_organization);
				} else {
					swal("Cancelled", "Remove Operation Canceled", "error");
				}
			});
		}

		function actual_removal(coming_user, callback){
			removeA(vm.pAOrganization.pausers, coming_user);
			callback();
		}

		vm.add_user = function(coming_user){
			actual_insertion(coming_user, save_organization);
		}
		function actual_insertion(coming_user, callback){
			vm.pAOrganization.pausers.push(coming_user);
			callback();
		}

		function save_organization () {
			vm.isSaving = true;
			if (vm.pAOrganization.id !== null) {
				PAOrganization.update(vm.pAOrganization, onSaveSuccess, onSaveError);
			} else {
				PAOrganization.save(vm.pAOrganization, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess (result) {
			swal({
				title: "Success!",
				text: "Organization updated successfully",
				type: "success"
			});
			$scope.$emit('pasaasApp:pAOrganizationUpdate', result);
			vm.isSaving = false;
			$state.reload();
		}

		function onSaveError () {
			swal({
				title: "Error!",
				text: "Error while updating organization",
				type: "error"
			});
			vm.isSaving = false;
		}

		function removeA(arr) {
			var what, a = arguments, L = a.length, ax;
			while (L > 1 && arr.length) {
				what = a[--L];
				while ((ax= arr.indexOf(what)) !== -1) {
					arr.splice(ax, 1);
				}
			}
			return arr;
		}

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

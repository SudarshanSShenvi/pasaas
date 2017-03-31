(function() {
	'use strict';

	angular
		.module('pasaasApp')
		.controller('PAProjectDialogController', PAProjectDialogController);

	PAProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAProject', 'PAOrganization', 'PAErrorMessage', 'PANotification', 'PAFileUpload', 'PAAccPrecision', 'PAPrediction', 'PAAlarmActuality', 'PASaxCode', 'PASaxCodeTmp', 'PAPredictionScore', 'PAReliabilityConf', 'PAReliabilityScore', 'PAPMTRequest', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails', 'PADataConnector', 'PAScheduler'];

	function PAProjectDialogController ( $timeout, $scope, $stateParams, $uibModalInstance, entity, PAProject, PAOrganization, PAErrorMessage, PANotification, PAFileUpload, PAAccPrecision, PAPrediction, PAAlarmActuality, PASaxCode, PASaxCodeTmp, PAPredictionScore, PAReliabilityConf, PAReliabilityScore, PAPMTRequest, PASchedulerInterval, PAAlarmRCA, PANEDetails, PADataConnector, PAScheduler) {
		var vm = this;

	// PAProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'PAProject', 'PAOrganization', 'PAErrorMessage', 'PANotification', 'PAFileUpload', 'PAAccPrecision', 'PAPrediction', 'PAAlarmActuality', 'PASaxCode', 'PASaxCodeTmp', 'PAPredictionScore', 'PAReliabilityConf', 'PAReliabilityScore', 'PAPMTRequest', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails', 'PADataConnector', 'PAScheduler'];

	// function PAProjectDialogController ($timeout, $scope, $stateParams, entity, PAProject, PAOrganization, PAErrorMessage, PANotification, PAFileUpload, PAAccPrecision, PAPrediction, PAAlarmActuality, PASaxCode, PASaxCodeTmp, PAPredictionScore, PAReliabilityConf, PAReliabilityScore, PAPMTRequest, PASchedulerInterval, PAAlarmRCA, PANEDetails, PADataConnector, PAScheduler) {
	//     var vm = this;

		vm.pAProject = entity;
		vm.clear = clear;
		vm.save = save;

		
		/*vm.this_organization = $rootScope.this_organization;
		alert(vm.this_organization);
*/
		vm.paorganizations = PAOrganization.query();
		/*
		vm.paerrormessages = PAErrorMessage.query();
		vm.panotifications = PANotification.query();
		vm.pafileuploads = PAFileUpload.query();
		vm.paaccprecisions = PAAccPrecision.query();
		vm.papredictions = PAPrediction.query();
		vm.paalarmactualities = PAAlarmActuality.query();
		vm.pasaxcodes = PASaxCode.query();
		vm.pasaxcodetmps = PASaxCodeTmp.query();
		vm.papredictionscores = PAPredictionScore.query();
		vm.pareliabilityconfs = PAReliabilityConf.query();
		vm.pareliabilityscores = PAReliabilityScore.query();
		vm.papmtrequests = PAPMTRequest.query();
		vm.paschedulerintervals = PASchedulerInterval.query();
		vm.paalarmrcas = PAAlarmRCA.query();
		vm.panedetails = PANEDetails.query();
		vm.padataconnectors = PADataConnector.query();
		vm.paschedulers = PAScheduler.query();*/

		vm.move_participants = function(from_array, to_array, coming_user){
			// var from_array = vm.pAProject.pausers;
			// var to_array = vm.paorganizations[0].pausers;
			console.log("From To Now " + coming_user);
			if(!vm.checkAvailability(to_array, coming_user)){
				console.log("Inside checkAvailability");
				to_array.push(coming_user);
			}
			vm.remove_form_array(from_array, coming_user);
		}

		vm.checkAvailability = function(arr, val) {
			return arr.some(arrVal => val === arrVal);
		}

		vm.remove_form_array = function(arr) {
		    var what, a = arguments, L = a.length, ax;
		    while (L > 1 && arr.length) {
		        what = a[--L];
		        while ((ax= arr.indexOf(what)) !== -1) {
		            arr.splice(ax, 1);
		        }
		    }
		    return arr;
		}
		

		$timeout(function (){
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear () {
			$uibModalInstance.dismiss('cancel');
		}

		function save () {
			console.log("Saving New Project");
			vm.isSaving = true;
			if (vm.pAProject.id !== null) {
				PAProject.update(vm.pAProject, onSaveSuccess, onSaveError);
			} else {
				PAProject.save(vm.pAProject, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess (result) {
			$scope.$emit('pasaasApp:pAProjectUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError () {
			vm.isSaving = false;
		}


	}
})();

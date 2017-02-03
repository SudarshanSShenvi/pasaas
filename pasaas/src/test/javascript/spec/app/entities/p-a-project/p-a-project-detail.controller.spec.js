'use strict';

describe('Controller Tests', function() {

    describe('PAProject Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAProject, MockPAOrganization, MockPAErrorMessage, MockPANotification, MockPAFileUpload, MockPAAccPrecision, MockPAPrediction, MockPAAlarmActuality, MockPASaxCode, MockPASaxCodeTmp, MockPAPredictionScore, MockPAReliabilityConf, MockPAReliabilityScore, MockPAPMTRequest, MockPASchedulerInterval, MockPAAlarmRCA, MockPANEDetails, MockPADataConnector, MockPAScheduler;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAProject = jasmine.createSpy('MockPAProject');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAErrorMessage = jasmine.createSpy('MockPAErrorMessage');
            MockPANotification = jasmine.createSpy('MockPANotification');
            MockPAFileUpload = jasmine.createSpy('MockPAFileUpload');
            MockPAAccPrecision = jasmine.createSpy('MockPAAccPrecision');
            MockPAPrediction = jasmine.createSpy('MockPAPrediction');
            MockPAAlarmActuality = jasmine.createSpy('MockPAAlarmActuality');
            MockPASaxCode = jasmine.createSpy('MockPASaxCode');
            MockPASaxCodeTmp = jasmine.createSpy('MockPASaxCodeTmp');
            MockPAPredictionScore = jasmine.createSpy('MockPAPredictionScore');
            MockPAReliabilityConf = jasmine.createSpy('MockPAReliabilityConf');
            MockPAReliabilityScore = jasmine.createSpy('MockPAReliabilityScore');
            MockPAPMTRequest = jasmine.createSpy('MockPAPMTRequest');
            MockPASchedulerInterval = jasmine.createSpy('MockPASchedulerInterval');
            MockPAAlarmRCA = jasmine.createSpy('MockPAAlarmRCA');
            MockPANEDetails = jasmine.createSpy('MockPANEDetails');
            MockPADataConnector = jasmine.createSpy('MockPADataConnector');
            MockPAScheduler = jasmine.createSpy('MockPAScheduler');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAProject': MockPAProject,
                'PAOrganization': MockPAOrganization,
                'PAErrorMessage': MockPAErrorMessage,
                'PANotification': MockPANotification,
                'PAFileUpload': MockPAFileUpload,
                'PAAccPrecision': MockPAAccPrecision,
                'PAPrediction': MockPAPrediction,
                'PAAlarmActuality': MockPAAlarmActuality,
                'PASaxCode': MockPASaxCode,
                'PASaxCodeTmp': MockPASaxCodeTmp,
                'PAPredictionScore': MockPAPredictionScore,
                'PAReliabilityConf': MockPAReliabilityConf,
                'PAReliabilityScore': MockPAReliabilityScore,
                'PAPMTRequest': MockPAPMTRequest,
                'PASchedulerInterval': MockPASchedulerInterval,
                'PAAlarmRCA': MockPAAlarmRCA,
                'PANEDetails': MockPANEDetails,
                'PADataConnector': MockPADataConnector,
                'PAScheduler': MockPAScheduler
            };
            createController = function() {
                $injector.get('$controller')("PAProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAProjectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

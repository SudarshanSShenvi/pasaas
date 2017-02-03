'use strict';

describe('Controller Tests', function() {

    describe('PAPredictionScore Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAPredictionScore, MockPAOrganization, MockPAProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAPredictionScore = jasmine.createSpy('MockPAPredictionScore');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAProject = jasmine.createSpy('MockPAProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAPredictionScore': MockPAPredictionScore,
                'PAOrganization': MockPAOrganization,
                'PAProject': MockPAProject
            };
            createController = function() {
                $injector.get('$controller')("PAPredictionScoreDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAPredictionScoreUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

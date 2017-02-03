'use strict';

describe('Controller Tests', function() {

    describe('PAPrediction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAPrediction, MockPAOrganization, MockPAProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAPrediction = jasmine.createSpy('MockPAPrediction');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAProject = jasmine.createSpy('MockPAProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAPrediction': MockPAPrediction,
                'PAOrganization': MockPAOrganization,
                'PAProject': MockPAProject
            };
            createController = function() {
                $injector.get('$controller')("PAPredictionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAPredictionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

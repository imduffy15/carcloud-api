'use strict';

describe('Services Tests ', function () {

    beforeEach(module('carcloudApp'));

    describe('AuthenticationSharedService', function () {
        var serviceTested,
            httpBackend,
            authServiceSpied;

        beforeEach(inject(function ($httpBackend, AuthenticationSharedService, authService) {
            serviceTested = AuthenticationSharedService;
            httpBackend = $httpBackend;
            authServiceSpied = authService;
        }));
        //make sure no expectations were missed in your tests.
        //(e.g. expectGET or expectPOST)
        afterEach(function () {
            httpBackend.verifyNoOutstandingExpectation();
            httpBackend.verifyNoOutstandingRequest();
        });

        it('should call backend on logout then call authService.loginCancelled', function () {
            //GIVEN
            //set up some data for the http call to return and test later.
            var returnData = { result: 'ok' };
            //expectGET to make sure this is called once.
            httpBackend.expectGET('http://localhost:8080/app/logout').respond(returnData);

            //Set spy
            spyOn(authServiceSpied, 'loginCancelled');

            //WHEN
            serviceTested.logout();
            //flush the backend to "execute" the request to do the expectedGET assertion.
            httpBackend.flush();

            //THEN
            expect(authServiceSpied.loginCancelled).toHaveBeenCalled();
        });

    });
});

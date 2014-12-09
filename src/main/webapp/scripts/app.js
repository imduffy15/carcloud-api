'use strict';

/* App Module */
var httpHeaders;

var carcloudApp = angular.module('carcloudApp', ['http-auth-interceptor',
    'ngResource', 'ngRoute', 'ngCookies', 'carcloudAppUtils', 'truncate', 'hateoas']);

carcloudApp
    .config(function ($routeProvider, $httpProvider, $sceDelegateProvider, HateoasInterceptorProvider, USER_ROLES) {
        $routeProvider
            .when('/register', {
                templateUrl: 'views/register.html',
                controller: 'RegisterController',
                access: {
                    authorities: [USER_ROLES.all]
                }
            })
            .when('/activate', {
                templateUrl: 'views/activate.html',
                controller: 'ActivationController',
                access: {
                    authorities: [USER_ROLES.all]
                }
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController',
                access: {
                    authorities: [USER_ROLES.all]
                }
            })
            .when('/error', {
                templateUrl: 'views/error.html',
                access: {
                    authorities: [USER_ROLES.all]
                }
            })
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'SettingsController',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/password', {
                templateUrl: 'views/password.html',
                controller: 'PasswordController',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/metrics', {
                templateUrl: 'views/metrics.html',
                controller: 'MetricsController',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/logs', {
                templateUrl: 'views/logs.html',
                controller: 'LogsController',
                resolve: {
                    resolvedLogs: ['LogsService', function (LogsService) {
                        return LogsService.findAll();
                    }]
                },
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/audits', {
                templateUrl: 'views/audits.html',
                controller: 'AuditsController',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/logout', {
                templateUrl: 'views/main.html',
                controller: 'LogoutController',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .when('/docs', {
                templateUrl: 'views/docs.html',
                access: {
                    authorities: [USER_ROLES.user]
                }
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController',
                access: {
                    authorities: [USER_ROLES.all]
                }
            });

        HateoasInterceptorProvider.transformAllResponses();
        httpHeaders = $httpProvider.defaults.headers;

    })
    .run(function ($rootScope, $location, $http, AuthenticationSharedService, Session, USER_ROLES) {

        $rootScope.authenticated = !!Session.get();
        $rootScope.account = Session.get();

        // SPAM 0.0.1 - Example of event listeners
        $rootScope.$on('$routeChangeStart', function (event, next) {
            $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
            $rootScope.userRoles = USER_ROLES;
            AuthenticationSharedService.valid(next.access.authorities);
        });

        // Call when the the client is confirmed
        // SPAM 0.0.1 - Example of event listeners
        $rootScope.$on('event:auth-loginConfirmed', function (data) {
            $rootScope.authenticated = true;
            $rootScope.account = Session.get();
            if ($location.path() === "/login") {
                $location.path('/');
            }
        });

        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function(rejection) {
            Session.invalidate();
            $rootScope.authenticated = false;
            if ($location.path() !== "/" && $location.path() !== "" && $location.path() !== "/register" && $location.path() !== "/login") {
                $location.path('/login');
            }
        });

        // Call when the 403 response is returned by the server
        // SPAM 0.0.1 - Example of event listeners
        $rootScope.$on('event:auth-notAuthorized', function (rejection) {
            $rootScope.errorMessage = 'You are not authorized to access the page.';
            $location.path('/error');
        });

        // Call when the user logs out
        // SPAM 0.0.1 - Example of event listeners
        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('');
        });
    });

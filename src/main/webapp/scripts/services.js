'use strict';

/* Services */

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('Register', function ($resource) {
    return $resource('app/rest/register', {}, {});
});

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('Activate', function ($resource) {
    return $resource('app/rest/activate', {}, {
        'get': {method: 'GET', params: {}, isArray: false}
    });
});

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('Account', function ($resource) {
    return $resource('app/rest/account', {}, {});
});

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('Password', function ($resource) {
    return $resource('app/rest/account/change_password', {}, {});
});

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('MetricsService', function ($resource) {
    return $resource('metrics/metrics', {}, {
        'get': {method: 'GET'}
    });
});

carcloudApp.factory('ThreadDumpService', function ($http) {
    return {
        dump: function () {
            return $http.get('dump').then(function (response) {
                return response.data;
            });
        }
    };
});

carcloudApp.factory('HealthCheckService', function ($rootScope, $http) {
    return {
        check: function () {
            return $http.get('health').then(function (response) {
                return response.data;
            });
        }
    };
});

carcloudApp.factory('LogsService', function ($resource) {
    return $resource('app/rest/logs', {}, {
        'findAll': {method: 'GET', isArray: true},
        'changeLevel': {method: 'PUT'}
    });
});

carcloudApp.factory('AuditsService', function ($http) {
    return {
        findAll: function () {
            return $http.get('app/rest/audits/all').then(function (response) {
                return response.data;
            });
        },
        findByDates: function (fromDate, toDate) {
            return $http.get('app/rest/audits/byDates', {
                params: {
                    fromDate: fromDate,
                    toDate: toDate
                }
            }).then(function (response) {
                return response.data;
            });
        }
    }
});

carcloudApp.factory('AuthenticationSharedService', function ($rootScope, $http, $location, authService, Session, Account, Base64Service, Token) {
    return {
        login: function (param) {
            var data = "username=" + param.username + "&password=" + param.password + "&grant_type=password&scope=read%20write&client_secret=Echoong7zooNga3tvohy6Xaeoon9Aem3ange8Iga5ooDa1ahb8LaS2&client_id=carcloudapp";
            $http.post('/oauth/token', data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "Accept": "application/json",
                    "Authorization": "Basic " + Base64Service.encode("carcloudapp" + ':' + "Echoong7zooNga3tvohy6Xaeoon9Aem3ange8Iga5ooDa1ahb8LaS2")
                },
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                httpHeaders.common['Authorization'] = 'Bearer ' + data.access_token;
                Token.set(data);

                Session.create().then(function() {
                    authService.loginConfirmed(null);
                });
            }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        refresh: function () {
            var data = "refresh_token=" + Token.get('refresh_token') + "&grant_type=refresh_token&client_secret=mySecretOAuthSecret&client_id=carcloudapp";
            $http.post('/oauth/token', data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "Accept": "application/json",
                    "Authorization": "Basic " + Base64Service.encode("carcloudapp" + ':' + "Echoong7zooNga3tvohy6Xaeoon9Aem3ange8Iga5ooDa1ahb8LaS2")
                },
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                if (data.access_token) httpHeaders.common['Authorization'] = 'Bearer ' + data.access_token;
                Token.set(data);

                Session.create().then(function() {
                    authService.loginConfirmed(null, function (config) {
                        config.headers['Authorization'] = 'Bearer ' + Token.get('access_token');
                        return config;
                    });
                });

            }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        valid: function (authorities) {
            if (Token.get('access_token')) httpHeaders.common['Authorization'] = 'Bearer ' + Token.get('access_token');
            if($rootScope.authenticated) {
                if (!$rootScope.isAuthorized(authorities)) {
                    event.preventDefault();
                    $rootScope.$broadcast("event:auth-notAuthorized");
                }
            }
        },
        isAuthorized: function (authorities) {
            if (!angular.isArray(authorities)) {
                if (authorities == '*') {
                    return true;
                }

                authorities = [authorities];
            }

            var isAuthorized = false;
            angular.forEach(authorities, function (authority) {
                var authorized = ($rootScope.account.authorities.indexOf(authority) !== -1);
                if (authorized || authorities == '*') {
                    isAuthorized = true;
                }
            });

            return isAuthorized;
        },
        logout: function () {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            Token.remove();

            $http.get('app/logout');
            Session.invalidate();
            delete httpHeaders.common['Authorization'];
            authService.loginCancelled();
        }
    };
});

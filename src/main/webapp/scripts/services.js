'use strict';

/* Services */

carcloudApp.factory('Register', function ($resource) {
    return $resource('http://localhost:8080/app/rest/register', {}, {
    });
});

carcloudApp.factory('Activate', function ($resource) {
    return $resource('http://localhost:8080/app/rest/activate', {}, {
        'get': { method: 'GET', params: {}, isArray: false}
    });
});

carcloudApp.factory('Account', function ($resource) {
    return $resource('http://localhost:8080/app/rest/account', {}, {
    });
});

carcloudApp.factory('Password', function ($resource) {
    return $resource('http://localhost:8080/app/rest/account/change_password', {}, {
    });
});

carcloudApp.factory('MetricsService', function ($resource) {
    return $resource('http://localhost:8080/metrics/metrics', {}, {
        'get': { method: 'GET'}
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
            return $http.get('http://localhost:8080/health').then(function (response) {
                return response.data;
            });
        }
    };
});

carcloudApp.factory('LogsService', function ($resource) {
    return $resource('http://localhost:8080/app/rest/logs', {}, {
        'findAll': { method: 'GET', isArray: true},
        'changeLevel': { method: 'PUT'}
    });
});

carcloudApp.factory('AuditsService', function ($http) {
    return {
        findAll: function () {
            return $http.get('http://localhost:8080/app/rest/audits/all').then(function (response) {
                return response.data;
            });
        },
        findByDates: function (fromDate, toDate) {
            return $http.get('http://localhost:8080/app/rest/audits/byDates', {params: {fromDate: fromDate, toDate: toDate}}).then(function (response) {
                return response.data;
            });
        }
    }
});

carcloudApp.factory('Session', function () {
    this.create = function (login, firstName, lastName, email, userRoles) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userRoles = userRoles;
    };
    this.invalidate = function () {
        this.login = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.userRoles = null;
    };
    return this;
});

carcloudApp.factory('AuthenticationSharedService', function ($rootScope, $http, authService, Session, Account, Base64Service, Token) {
    return {
        login: function (param) {
            var data = "username=" + param.username + "&password=" + param.password + "&grant_type=password&scope=read%20write&client_secret=Echoong7zooNga3tvohy6Xaeoon9Aem3ange8Iga5ooDa1ahb8LaS2&client_id=carcloudapp";
            $http.post('http://localhost:8080/oauth/token', data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "Accept": "application/json",
                    "Authorization": "Basic " + Base64Service.encode("carcloudapp" + ':' + "Echoong7zooNga3tvohy6Xaeoon9Aem3ange8Iga5ooDa1ahb8LaS2")
                },
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                httpHeaders.common['Authorization'] = 'Bearer ' + data.access_token;
                Token.set(data);

                Account.get(function (data) {
                    Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                    $rootScope.account = Session;
                    authService.loginConfirmed(data);
                });
            }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        refresh: function () {
            var data = "refresh_token=" + Token.get('refresh_token') + "&grant_type=refresh_token&client_secret=mySecretOAuthSecret&client_id=carcloudapp";
            $http.post('oauth/token', data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "Accept": "application/json",
                    "Authorization": "Basic " + Base64Service.encode("carcloudapp" + ':' + "mySecretOAuthSecret")
                },
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                if (data.access_token) httpHeaders.common['Authorization'] = 'Bearer ' + data.access_token;
                Token.set(data);

                Account.get(function (data) {
                    Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                    $rootScope.account = Session;
                    authService.loginConfirmed(data, function (config) {
                        console.log("setting new header");
                        config.headers['Authorization'] = 'Bearer ' + Token.get('access_token');
                        return config;
                    });
                });
            }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                Session.invalidate();
            });
        },
        valid: function (authorizedRoles) {
            if (Token.get('access_token')) httpHeaders.common['Authorization'] = 'Bearer ' + Token.get('access_token');

            $http.get('protected/authentication_check.gif', {
                ignoreAuthModule: 'ignoreAuthModule'
            }).success(function (data, status, headers, config) {
                if (!Session.login || Token.get('access_token') != undefined) {
                    if (Token.get('access_token') == undefined || Token.expired()) {
                        $rootScope.authenticated = false;
                        return;
                    }
                    Account.get(function (data) {
                        Session.create(data.login, data.firstName, data.lastName, data.email, data.roles);
                        $rootScope.account = Session;

                        if (!$rootScope.isAuthorized(authorizedRoles)) {
                            event.preventDefault();
                            // user is not allowed
                            $rootScope.$broadcast("event:auth-notAuthorized");
                        }

                        $rootScope.authenticated = true;
                    });
                }
                $rootScope.authenticated = !!Session.login;
            }).error(function (data, status, headers, config) {
                $rootScope.authenticated = false;
            });
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }

                authorizedRoles = [authorizedRoles];
            }

            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!Session.login &&
                    Session.userRoles.indexOf(authorizedRole) !== -1);

                if (authorized || authorizedRole == '*') {
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

            $http.get('http://localhost:8080/app/logout');
            Session.invalidate();
            delete httpHeaders.common['Authorization'];
            authService.loginCancelled();
        }
    };
});

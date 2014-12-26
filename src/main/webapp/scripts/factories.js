'use strict';

carcloudApp.factory('Register', function ($resource) {
    return $resource('app/rest/register', {}, {});
});

carcloudApp.factory('Account', function ($resource) {
    return $resource('app/rest/account', {}, {
        'update': {method: 'PUT'}
    });
});

carcloudApp.factory('Password', function ($resource) {
    return $resource('app/rest/account/change_password', {}, {});
});

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

carcloudApp.factory('Session', function (localStorageService, $rootScope) {

    var session = {};

    session.set = function (username, firstName, lastName, email, authorities) {
        var data = localStorageService.get('session') || {};
        if (username) {
            data.username = username;
        }
        if (firstName) {
            data.firstName = firstName;
        }
        if (lastName) {
            data.lastName = lastName;
        }
        if (email) {
            data.email = email;
        }
        if (authorities) {
            data.authorities = authorities;
        }
        $rootScope.account = data;
        localStorageService.set('session', data);
    };

    session.invalidate = function () {
        $rootScope.account = {};
        localStorageService.remove('session');
    };

    session.get = function () {
        return localStorageService.get('session');
    };

    return session;
});

carcloudApp.factory('Token', function (localStorageService) {
    var token = {};

    token.get = function () {
        return localStorageService.get('token');
    };

    token.set = function (oauthResponse) {
        var data = localStorageService.get('token') || {};
        if (oauthResponse.access_token) {
            httpHeaders.common['Authorization'] = 'Bearer ' + oauthResponse.access_token;
            data.accessToken = oauthResponse.access_token
        }
        if (oauthResponse.refresh_token) {
            data.refreshToken = oauthResponse.refresh_token;
        }
        if (oauthResponse.expires_in) {
            data.expiresAt = setExpiresAt(oauthResponse, data);
        }
        localStorageService.set('token', data);
    };

    token.invalidate = function () {
        localStorageService.remove('token');
    };

    var setExpiresAt = function (oauthResponse) {
        var now = new Date();
        var minutes = parseInt(oauthResponse.expires_in) / 60;
        return new Date(now.getTime() + minutes * 60000).getTime()
    };

    return token;
});

carcloudApp.factory('AuthenticationService',
                    function ($base64, $http, $rootScope, Account, authService, API_DETAILS,
                              Session, Token) {
                        var authenticationService = {};

                        var request = {
                            'grant_type': API_DETAILS.grantType,
                            'scope': API_DETAILS.scope,
                            'client_secret': API_DETAILS.clientSecret,
                            'client_id': API_DETAILS.clientId
                        };

                        var authenticationSuccess = function (data, status, headers, configata) {
                            Token.set(data);

                            Account.get().$promise.then(
                                function (account) {
                                    account.resource("authorities").query().$promise.then(
                                        function (authorities) {
                                            Session.set(
                                                account.username,
                                                account.firstName,
                                                account.lastName,
                                                account.email,
                                                authorities
                                            );
                                            authService.loginConfirmed();
                                        },
                                        function (data, status, headers, config) {
                                            authenticationError(data, status, headers, config)
                                        }
                                    );
                                },
                                function (data, status, headers, config) {
                                    authenticationError(data, status, headers, config)
                                }
                            )
                        };

                        var authenticationError = function (data, status, headers, config) {
                            Token.invalidate();
                            Session.invalidate();
                        };

                        authenticationService.login = function (credentials) {
                            var loginRequest = {};
                            angular.extend(loginRequest, credentials);
                            angular.extend(loginRequest, request);

                            loginRequest = jQuery.param(loginRequest);
                            loginRequest = loginRequest.replace('+', '%20');

                            $http.post(API_DETAILS.baseUrl + 'oauth/token', loginRequest, {
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded',
                                    "Authorization": "Basic " + $base64.encode(API_DETAILS.clientId
                                                                               + ':'
                                                                               + API_DETAILS.clientSecret)
                                }
                            })
                                .success(function (data, status, headers, config) {
                                             authenticationSuccess(data, status, headers, config)
                                         })
                                .error(function (data, status, headers, config) {
                                           authenticationError(data, status, headers, config)
                                       });
                        };

                        authenticationService.logout = function () {
                            $rootScope.authenticated = false;
                            $rootScope.authenticationError = false;

                            Token.invalidate();
                            $http.get(API_DETAILS.baseUrl + 'app/logout');
                            Session.invalidate();
                            delete httpHeaders.common['Authorization'];
                            authService.loginCancelled();
                        };

                        return authenticationService;
                    });

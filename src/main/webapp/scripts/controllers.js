'use strict';

/* Controllers */

carcloudApp.controller('MainController', function ($scope) {
});

carcloudApp.controller('AdminController', function ($scope) {
});

carcloudApp.controller('MenuController', function ($scope) {
});

carcloudApp.controller('LoginController', function ($scope, $location, AuthenticationService) {
    $scope.rememberMe = true;
    $scope.login = function () {
        AuthenticationService.login({
                                        username: $scope.username,
                                        password: $scope.password
                                    });
    }
});

carcloudApp.controller('LogoutController', function ($location, AuthenticationService) {
    AuthenticationService.logout();
});

carcloudApp.controller('SettingsController', function ($rootScope, $scope, Account, Session) {
    $scope.success = null;
    $scope.error = null;
    $scope.settingsAccount = Account.get();

    $scope.save = function () {
        Account.update($scope.settingsAccount,
                       function (value, responseHeaders) {
                           $scope.error = null;
                           $scope.success = 'OK';
                           Account.get().$promise.then(function (data) {
                               $scope.settingsAccount = data;
                               Session.set(
                                   $scope.settingsAccount.username,
                                   $scope.settingsAccount.firstName,
                                   $scope.settingsAccount.lastName,
                                   $scope.settingsAccount.email
                               );
                           });
                       },
                       function (httpResponse) {
                           $scope.success = null;
                           $scope.error = "ERROR";
                       });
    };
});

carcloudApp.controller('RegisterController', function ($scope, Register) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.errorUserExists = null;
    $scope.register = function () {
        if ($scope.registerAccount.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.doNotMatch = null;
            Register.save($scope.registerAccount,
                          function (value, responseHeaders) {
                              $scope.error = null;
                              $scope.errorUserExists = null;
                              $scope.success = 'OK';
                          },
                          function (httpResponse) {
                              $scope.success = null;
                              if (httpResponse.status === 304) {
                                  $scope.error = null;
                                  $scope.errorUserExists = "ERROR";
                              } else {
                                  $scope.error = "ERROR";
                                  $scope.errorUserExists = null;
                              }
                          });
        }
    }
});

carcloudApp.controller('PasswordController', function ($scope, Password) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.changePassword = function () {
        if ($scope.password != $scope.confirmPassword) {
            $scope.doNotMatch = "ERROR";
        } else {
            $scope.doNotMatch = null;
            Password.save($scope.password,
                          function (value, responseHeaders) {
                              $scope.error = null;
                              $scope.success = 'OK';
                          },
                          function (httpResponse) {
                              $scope.success = null;
                              $scope.error = "ERROR";
                          });
        }
    };
});

carcloudApp.controller('MetricsController',
                       function ($scope, MetricsService, HealthCheckService, ThreadDumpService) {

                           $scope.refresh = function () {
                               HealthCheckService.check().then(function (promise) {
                                   $scope.healthCheck = promise;
                               }, function (promise) {
                                   $scope.healthCheck = promise;
                               });

                               $scope.metrics = MetricsService.get();

                               $scope.metrics.$get({}, function (items) {

                                   $scope.servicesStats = {};
                                   $scope.cachesStats = {};
                                   angular.forEach(items.timers, function (value, key) {
                                       if (key.indexOf("web.rest") != -1 || key.indexOf("service")
                                                                            != -1) {
                                           $scope.servicesStats[key] = value;
                                       }

                                       if (key.indexOf("net.sf.ehcache.Cache") != -1) {
                                           // remove gets or puts
                                           var index = key.lastIndexOf(".");
                                           var newKey = key.substr(0, index);

                                           // Keep the name of the domain
                                           index = newKey.lastIndexOf(".");
                                           $scope.cachesStats[newKey] = {
                                               'name': newKey.substr(index + 1),
                                               'value': value
                                           };
                                       }
                                   });
                               });
                           };

                           $scope.refresh();

                           $scope.threadDump = function () {
                               ThreadDumpService.dump().then(function (data) {
                                   $scope.threadDump = data;

                                   $scope.threadDumpRunnable = 0;
                                   $scope.threadDumpWaiting = 0;
                                   $scope.threadDumpTimedWaiting = 0;
                                   $scope.threadDumpBlocked = 0;

                                   angular.forEach(data, function (value, key) {
                                       if (value.threadState == 'RUNNABLE') {
                                           $scope.threadDumpRunnable += 1;
                                       } else if (value.threadState == 'WAITING') {
                                           $scope.threadDumpWaiting += 1;
                                       } else if (value.threadState == 'TIMED_WAITING') {
                                           $scope.threadDumpTimedWaiting += 1;
                                       } else if (value.threadState == 'BLOCKED') {
                                           $scope.threadDumpBlocked += 1;
                                       }
                                   });

                                   $scope.threadDumpAll =
                                   $scope.threadDumpRunnable + $scope.threadDumpWaiting +
                                   $scope.threadDumpTimedWaiting + $scope.threadDumpBlocked;

                               });
                           };

                           $scope.getLabelClass = function (threadState) {
                               if (threadState == 'RUNNABLE') {
                                   return "label-success";
                               } else if (threadState == 'WAITING') {
                                   return "label-info";
                               } else if (threadState == 'TIMED_WAITING') {
                                   return "label-warning";
                               } else if (threadState == 'BLOCKED') {
                                   return "label-danger";
                               }
                           };
                       });



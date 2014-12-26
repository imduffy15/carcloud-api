'use strict';

carcloudApp
    .config(function ($routeProvider, $httpProvider, USER_ROLES) {
                $routeProvider
                    .when('/device', {
                              templateUrl: 'views/devices.html',
                              controller: 'DeviceController',
                              resolve: {
                                  resolvedDevice: ['Device', function (Device) {
                                      return Device.query();
                                  }]
                              },
                              access: {
                                  authorities: [USER_ROLES.all]
                              }
                          })
            });

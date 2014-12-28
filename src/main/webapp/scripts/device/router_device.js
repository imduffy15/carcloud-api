'use strict';

carcloudApp
    .config(function ($routeProvider, $httpProvider, USER_ROLES) {
                $routeProvider
                    .when('/device', {
                              templateUrl: 'views/devices.html',
                              controller: 'DeviceListController',
                              resolve: {
                                  resolvedDevice: function (Device) {
                                      return Device.query();
                                  }
                              },
                              access: {
                                  authorities: [USER_ROLES.all]
                              }
                          })
                    .when('/device/:id', {
                              templateUrl: 'views/device.html',
                              controller: 'DeviceController',
                              resolve: {
                                  resolvedDevice: function ($route, $q, Device) {

                                      var deferred = $q.defer();

                                      Device.get({id: $route.current.params.id}, function(device) {
                                          device.resource("tracks").query().$promise.then(function(tracks) {
                                              device.tracks = tracks;
                                              deferred.resolve(device);
                                          });
                                      });

                                      return deferred.promise;
                                  }
                              },
                              access: {
                                  authorities: [USER_ROLES.all]
                              }
                          })
            });

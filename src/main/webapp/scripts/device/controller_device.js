'use strict';

carcloudApp.controller('DeviceListController', function ($modal, $scope, $q, resolvedDevice, Device, User) {

    $scope.devices = resolvedDevice;

    $scope.selected = undefined;

    $scope.openAddDeviceModal = function() {
        $modal.open({
                        templateUrl: 'views/devices-add.html',
                        controller: 'DeviceAddController',
                        resolve: {
                            $parentScope : function() {
                                return $scope;
                            }
                        }
                    });
    };

    $scope.openEditDeviceModal = function(id) {
        $modal.open({
                        templateUrl: 'views/devices-edit.html',
                        controller: 'DeviceEditController',
                        resolve: {
                            $parentScope : function() {
                                return $scope;
                            },
                            resolvedDevice: function(Device) {
                                return Device.get({id: id});
                            }
                        }
                    });
    };

    $scope.openOwnersDeviceModal = function(id) {
        $modal.open({
                        templateUrl: 'views/devices-owners.html',
                        controller: 'DeviceOwnersController',
                        resolve: {
                            $parentScope : function() {
                                return $scope;
                            },
                            resolvedDevice: function(Device, $q) {
                                var deferred = $q.defer();

                                Device.get({'id': id}, function(device) {
                                   device.resource("owners").query().$promise.then(function(owners) {
                                       device.owners = {};
                                       angular.forEach(owners, function(owner) {
                                          device.owners[owner.username] = owner;
                                       });
                                       deferred.resolve(device);
                                   });
                                });

                                return deferred.promise;
                            }
                        }
                    });
    };

    $scope.delete = function (id) {
        Device.delete({id: id}, function() {
            delete $scope.devices[id];
        });
    };
});

carcloudApp.controller('DeviceController', function($scope, resolvedDevice) {

    $scope.device = resolvedDevice;

    $scope.tracks = [];

    angular.forEach(resolvedDevice.tracks, function(track) {
        $scope.tracks.push([track.latitude, track.longitude]);
    });

    $scope.center = $scope.tracks[Math.round(($scope.tracks.length - 1) / 2)];

});


carcloudApp.controller('DeviceAddController', function($scope, $parentScope, $modalInstance, Device) {
    $scope.create = function () {
        Device.save($scope.device,
                    function () {
                        Device.query().$promise.then(function(devices) {
                            angular.forEach(devices, function(device) {
                                if(!$parentScope.devices[device.id]) {
                                    $parentScope.devices[device.id] = device;
                                }
                            });
                        });
                        $modalInstance.close();
                    });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});

carcloudApp.controller('DeviceEditController', function($scope, $parentScope, $modalInstance, Device, resolvedDevice) {

    $scope.device = resolvedDevice;

    console.log(resolvedDevice);

    $scope.update = function () {
        Device.update($scope.device,
                      function () {
                          $parentScope.devices[$scope.device.id] = $scope.device;
                          $modalInstance.close();
                      });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});

carcloudApp.controller('DeviceOwnersController', function($scope, $parentScope, $modalInstance, Device, User, resolvedDevice) {

    $scope.device = resolvedDevice;

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.getUsers = function(username) {
        return User.get({'username': username}).$promise;
    };

    $scope.addOwner = function() {
        resolvedDevice.resource("owners").save($scope.usernameSelected, function () {

            $scope.device.resource("owners").query().$promise.then(function(owners) {
                angular.forEach(owners, function(owner) {
                    if(!$scope.device.owners[owner.username]) {
                        $scope.device.owners[owner.username] = owner;
                    }
                });
                $scope.usernameSelected = undefined;
            });

       });
    };
});

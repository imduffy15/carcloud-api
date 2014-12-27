'use strict';

carcloudApp.controller('DeviceController', function ($scope, resolvedDevice, Device) {

    $scope.devices = resolvedDevice;

    $scope.create = function () {
        Device.save($scope.device,
                    function () {
                        $scope.devices = Device.query();
                        $('#createDeviceModal').modal('hide');
                        $scope.clear();
                    });
    };

    $scope.update = function () {
        Device.update($scope.device,
                    function () {
                        $scope.devices = Device.query();
                        $('#updateDeviceModal').modal('hide');
                        $scope.clear();
                    });
    };


    $scope.edit = function (id) {
        $scope.device = Device.get({id: id});
        $('#updateDeviceModal').modal('show');
    };

    $scope.delete = function (id) {
        Device.delete({id: id},
                      function () {
                          $scope.devices = Device.query();
                      });
    };

    $scope.clear = function () {
        $scope.device = {id: null, description: null};
    };
});

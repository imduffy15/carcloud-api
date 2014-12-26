'use strict';

carcloudApp.controller('DeviceController', function ($scope, resolvedDevice, Device) {

    $scope.devices = resolvedDevice;

    $scope.create = function () {
        Device.save($scope.device,
                    function () {
                        $scope.devices = Device.query();
                        $('#saveDeviceModal').modal('hide');
                        $scope.clear();
                    });
    };

    $scope.update = function (id) {
        $scope.device = Device.get({id: id});
        $('#saveDeviceModal').modal('show');
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

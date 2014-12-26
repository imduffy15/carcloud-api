'use strict';

carcloudApp.controller('TrackController', function ($scope, resolvedTrack, Track) {

    $scope.tracks = resolvedTrack;

    $scope.create = function () {
        Track.save($scope.track,
                   function () {
                       $scope.tracks = Track.query();
                       $('#saveTrackModal').modal('hide');
                       $scope.clear();
                   });
    };

    $scope.update = function (id) {
        $scope.track = Track.get({id: id});
        $('#saveTrackModal').modal('show');
    };

    $scope.delete = function (id) {
        Track.delete({id: id},
                     function () {
                         $scope.tracks = Track.query();
                     });
    };

    $scope.clear = function () {
        $scope.track = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
    };
});

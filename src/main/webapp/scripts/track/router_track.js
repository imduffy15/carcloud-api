'use strict';

carcloudApp
    .config(function ($routeProvider, $httpProvider, USER_ROLES) {
                $routeProvider
                    .when('/track', {
                              templateUrl: 'views/tracks.html',
                              controller: 'TrackController',
                              resolve: {
                                  resolvedTrack: ['Track', function (Track) {
                                      return Track.query();
                                  }]
                              },
                              access: {
                                  authorities: [USER_ROLES.all]
                              }
                          })
            });

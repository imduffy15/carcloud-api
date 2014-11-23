'use strict';

// SPAM 0.0.1 - Example of facade on $resource

carcloudApp.factory('Device', function ($resource) {
    return $resource('app/rest/devices/:id', {}, {
        'query': {method: 'GET', isArray: true},
        'get': {method: 'GET'}
    });
});

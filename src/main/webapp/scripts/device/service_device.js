'use strict';

carcloudApp.factory('Device', function ($resource) {
    return $resource('app/rest/devices/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
});

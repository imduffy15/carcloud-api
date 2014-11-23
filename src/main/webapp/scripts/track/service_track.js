'use strict';

carcloudApp.factory('Track', function ($resource) {
        return $resource('app/rest/tracks/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

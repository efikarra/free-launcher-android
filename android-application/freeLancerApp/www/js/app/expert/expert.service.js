angular.module('e-lancer.expert')

.service("ExpertService", function ($resource,SERVER_IP_ADDRESS) {
        return $resource('http://'+SERVER_IP_ADDRESS.ipAddress+'/competitions/experts/:expId/:action/:id/:action2', {
            expId: '@expId',
            id: '@id'
        }, {
            get: {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            search: {
                method: 'GET',
                isArray: true,
                params: {
                    action: 'search'
                },
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            save: {
                method: 'POST',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            update: {
                method: 'PUT',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            projects: {
                params: {
                    action: 'projects'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
             bids: {
                params: {
                    action: 'bids'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
            
        });

    });
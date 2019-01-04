  angular.module('e-lancer.employer')
  .service("EmployerService", function ($resource,SERVER_IP_ADDRESS) {
        return $resource('http://'+SERVER_IP_ADDRESS.ipAddress+'/competitions/employers/:id1/:action1', {id1:'@id1'}, {
            get: {
                method: 'GET',
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
                    action1: 'projects'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        });

    });
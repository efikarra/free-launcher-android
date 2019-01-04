 angular.module('e-lancer.qualification')
 .service("QualificationService", function ($resource,SERVER_IP_ADDRESS) {
        return $resource('http://'+SERVER_IP_ADDRESS.ipAddress+'/competitions/qualifications', {}, {
            list: {
                method: 'GET',
                isArray: true
            }
        });
    });
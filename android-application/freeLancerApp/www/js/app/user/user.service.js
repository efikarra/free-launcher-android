angular.module('e-lancer.user')
.service("UserService", function ($resource,SERVER_IP_ADDRESS) {
        return $resource('http://'+SERVER_IP_ADDRESS.ipAddress+'/competitions/users/:userId/:action', {
            expId: '@userId',
        }, {
           
            updatePassword: {
                method: 'PUT',
                params: {
                action: 'updatePassword'
            }
        }
        });
})
;
angular.module('e-lancer')
 
.constant('AUTH_EVENTS', {
  notAuthenticated: 'auth-not-authenticated',
  notAuthorized: 'auth-not-authorized'
})
 
.constant('USER_ROLES', {
  employee: 'EMPLOYEE_ROLE',
  employer: 'EMPLOYER_ROLE'
})
.constant('SERVER_IP_ADDRESS', {
  ipAddress: '255.255.255.4:8080',
});
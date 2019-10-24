/**
 * 
 */
vod.config(function ($stateProvider,$urlRouterProvider) {
    $stateProvider.state('home',{
        url:'/',
        component:'home'
    });
    $urlRouterProvider.otherwise('/');
})
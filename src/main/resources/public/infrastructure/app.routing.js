/**
 * 
 */
vod.config(function ($stateProvider,$urlRouterProvider) {
    $stateProvider.state('home',{
        url:'/',
        component:'home'
    }).state('locations', {
        url:'/locations',
        component:'locations'
    }).state('login', {
        url:'/login',
        component:'login'
    }).state('payment', {
        url:'/pay',
        component:'payment'
    }).state('register', {
        url:'/register',
        component:'register'
    }).state('sports', {
        url:'/sports',
        component:'sports'
    }).state('videos', {
        url:'/videos',
        component:'vidoes'
    }).state('admin', {
        url:'/admin',
        component:'admin'
    }).state('gadmin', {
        url:'/gadmin',
        component:'gadmin'
    });
    $urlRouterProvider.otherwise('/');
})
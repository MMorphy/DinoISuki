vod.component('navBar', {
    template: `
<nav class="navbar nav-fill w-100 navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">go2play</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Locations</a>
      </li>

      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Sports
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Football</a>
          <a class="dropdown-item" href="#">Tennis</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      
      <li class="nav-item">
        <a class="nav-link" href="#">Videos</a>
      </li>
    </ul>
    <ul class="navbar-nav mr-right">
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Administration
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Location</a>
          <a class="dropdown-item" href="#">Global</a>
          <div class="dropdown-divider"></div>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Login</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Register</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="#">My Profile</a>
      </li>
    </ul>
  </div>
</nav>
   `,
    controller: function () {

    },
    controllerAs: 'c'
})
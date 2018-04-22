<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 4/21/2018
  Time: 2:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<body>
<div class="container">
  <div class="header">
      <div class="jumbotron">
          <img src="resources/images/logo.png" width="130px" class="float-left logo">
          <h1 class="display-4">Welcome to Cycling Club!</h1>
          <p class="lead">Go ride a bike!</p>
          <hr class="my-4">
          <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
          <p class="lead">
              <a class="btn btn-primary btn-lg" href="#" role="button" id="addEvent">Add Event</a>
          </p>
          <div class="add-event">
              <label for="eventName">Event name:</label>
              <input type="text" id="eventName" class="form-control"/>
          </div>
      </div>
    <a href="" class="btn btn-danger">Red Box</a>
  </div>
  <div class="content">
    <ul class="nav nav-tabs">
      <li class="active"><a class="nav-link active" data-toggle="tab" href="#upcoming">Upcoming rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#ongoing">"Live Cycling Rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#members">Members</a></li>
    </ul>

    <div class="tab-content">
      <div id="upcoming" class="tab-pane fade show active">
        <div class="card-columns">
          <div class="card">
            <img class="card-img-top" src="resources/images/245.svg" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title that wraps to a new line<span class="badge badge-secondary">New</span></h5>
                <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                <a href="#" class="card-link">Card link</a>
            </div>
          </div>
          <div class="card p-3">
            <blockquote class="blockquote mb-0 card-body">
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
              <footer class="blockquote-footer">
                <small class="text-muted">
                  Someone famous in <cite title="Source Title">Source Title</cite>
                </small>
              </footer>
            </blockquote>
          </div>
          <div class="card">
            <img class="card-img-top" src="resources/images/245.svg" alt="Card image cap">
            <div class="card-body">
              <h5 class="card-title">Card <span class="badge badge-secondary">New</span></h5>
              <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
              <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
              <a href="#" class="card-link">Card link</a>
            </div>
          </div>
          <div class="card bg-primary text-white text-center p-3">
            <blockquote class="blockquote mb-0">
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat.</p>
              <footer class="blockquote-footer">
                <small>
                  Someone famous in <cite title="Source Title">Source Title</cite>
                </small>
              </footer>
            </blockquote>
          </div>
          <div class="card text-center">
            <div class="card-body">
              <h5 class="card-title">Card title<span class="badge badge-secondary">New</span></h5>
              <p class="card-text">This card has supporting text below as a natural lead-in to additional content.</p>
              <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
            </div>
          </div>
          <div class="card">
            <img class="card-img" src="resources/images/260.svg" alt="Card image">
          </div>
          <div class="card p-3 text-right">
            <blockquote class="blockquote mb-0">
              <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
              <footer class="blockquote-footer">
                <small class="text-muted">
                  Someone famous in <cite title="Source Title">Source Title</cite>
                </small>
              </footer>
            </blockquote>
          </div>
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Card title</h5>
              <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>
              <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
            </div>
          </div>
        </div>
      </div>
      <div id="ongoing" class="tab-pane fade">
        <div id="accordion2">
          <div class="card">
            <div class="card-header" id="headingOne1">
              <h5 class="mb-0">
                <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne1" aria-expanded="true" aria-controls="collapseOne1">
                  Collapsible Group Item #1
                </button>
              </h5>
            </div>

            <div id="collapseOne1" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion2">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                  <div class="progress">
                      <div class="progress-bar progress-bar-striped bg-info" role="progressbar" style="width: 50%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                  </div>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
          <div class="card">
            <div class="card-header" id="headingTwo2">
              <h5 class="mb-0">
                <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo2" aria-expanded="false" aria-controls="collapseTwo2">
                  Collapsible Group Item #2
                </button>
              </h5>
            </div>
            <div id="collapseTwo2" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion2">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
          <div class="card">
            <div class="card-header" id="headingThree2">
              <h5 class="mb-0">
                <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseThree2" aria-expanded="false" aria-controls="collapseThree2">
                  Collapsible Group Item #3
                </button>
              </h5>
            </div>
            <div id="collapseThree2" class="collapse" aria-labelledby="headingThree" data-parent="#accordion2">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="members" class="tab-pane fade">
        <div id="accordion3">
          <div class="card">
            <div class="card-header" id="headingOne3">
              <h5 class="mb-0">
                <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne3" aria-expanded="true" aria-controls="collapseOne">
                  Collapsible Group Item #1
                </button>
              </h5>
            </div>

            <div id="collapseOne3" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion3">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
          <div class="card">
            <div class="card-header" id="headingTwo3">
              <h5 class="mb-0">
                <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo3" aria-expanded="false" aria-controls="collapseTwo3">
                  Collapsible Group Item #2
                </button>
              </h5>
            </div>
            <div id="collapseTwo3" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion3">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
          <div class="card">
            <div class="card-header" id="headingThree3">
              <h5 class="mb-0">
                <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseThree3" aria-expanded="false" aria-controls="collapseThree3">
                  Collapsible Group Item #3
                </button>
              </h5>
            </div>
            <div id="collapseThree3" class="collapse" aria-labelledby="headingThree" data-parent="#accordion3">
              <div class="card-body">
                <button class="btn btn-secondary float-right">Join a ride</button>
                Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</div>
</body>
</html>

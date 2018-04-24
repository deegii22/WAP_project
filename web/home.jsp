<%--
  Created by IntelliJ IDEA.
  User: deegii
  Date: 4/21/2018
  Time: 2:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<%
  if(session.getAttribute("user") == null){
      response.sendRedirect("login.jsp");
  }
%>
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
              <a class="btn btn-danger btn-lg" href="#" role="button" id="alertEvent">Red Box</a>
              <a class="btn btn-primary btn-lg" href='<% if(session.getAttribute("user") != null){ session.setMaxInactiveInterval(1); out.print("login.jsp");} %>' role="button" id="logout">Sign out</a>
          </p>
          <div class="add-event">
              <input type="text" id="eventName" name="eventName" placeholder="Event name" class="form-control"/>
              <input type="file" id="file" name="file" />
              <input type="date" id="start" name="start" placeholder="Start Datetime" class="form-control"/>
              <input type="date" id="end" name="end" placeholder="End Datetime" class="form-control"/>
              <div>
                  <input type="text" id="startPosition" name="startPosition" placeholder="Start" class="form-control"/>
                  <input type="text" id="endPosition" name="endPosition" placeholder="End" class="form-control"/>
                  <input type="text" id="duration" name="duration" placeholder="Duration" class="form-control"/>
                  <input type="submit" id="btnAddRoute" value="Save route"/>
                  <div id="dynamicTable">
                  </div>
              </div>
              <input type="submit" id="btnAddEvent" value="Save event"/>
          </div>
          <div class="alert alert-danger" role="alert">
              Event name:<br/>
              Ride participants:<br/>
              Current location:
          </div>
      </div>
  </div>
  <div class="content">
    <ul class="nav nav-tabs">
      <li class="active"><a class="nav-link active" data-toggle="tab" href="#upcoming">Upcoming rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#ongoing">Live Cycling Rides</a></li>
      <li class="nav-item"><a class="nav-link" data-toggle="tab" href="#members">Members</a></li>
    </ul>

    <div class="tab-content">
      <div id="upcoming" class="tab-pane fade show active">
        <div class="card-columns" id="columns">
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
          <div class="card-columns" id="columns1"></div>
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
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <%--<button type="button" class="btn btn-primary" id="">Join a ride</button>--%>
            </div>
        </div>
    </div>
</div>
</body>
</html>

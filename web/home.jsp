<%@ page import="com.model.User" %><%--
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

    User user = (User) session.getAttribute("user");
    String name = user.getName();
%>
<body>
<div class="container">
  <div class="header">
      <div class="jumbotron">
          <a class="btn btn-link logout" href='<% if(session.getAttribute("user") != null){ session.setMaxInactiveInterval(1); out.print("login.jsp");} %>' role="button" id="logout">Sign out</a>
          <img src="resources/images/logo.png" width="130px" class="float-left logo">
          <h1 class="display-4">Welcome <span class="user_name"><%=name%></span>, to Cycling Club! </h1>
          <p class="lead">Go ride a bike!</p>
          <hr class="my-4">
          <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
          <p class="lead">
              <a class="btn btn-success btn-lg" href="#" role="button" id="addEvent">Add Event</a>
              <a class="btn btn-secondary btn-lg" href="#" role="button" id="alertEvent">Red Box</a>
          </p>
          <div class="add-event">
              <form class="row">
                  <div class="col-sm-6">
                      <div class="form-group row">
                          <label for="eventName" class="col-sm-4 col-form-label">Event name:</label>
                          <div class="col-sm-6">
                            <input type="text" id="eventName" name="eventName" placeholder="Event name" class="form-control" required/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="file" class="col-sm-4 col-form-label">Event image:</label>
                          <div class="custom-file col-sm-6">
                              <input type="file" class="custom-file-input" id="file" name="file"/>
                              <label class="custom-file-label" for="file">Choose file</label>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="start" class="col-sm-4 col-form-label">Start date:</label>
                          <div class="col-sm-6">
                            <input type="datetime-local" id="start" name="start" placeholder="Start date" class="form-control" required/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="end" class="col-sm-4 col-form-label">End date:</label>
                          <div class="col-sm-6">
                                <input type="datetime-local" id="end" name="end" placeholder="End date" class="form-control" required/>
                          </div>
                      </div>
                  </div>
                  <fieldset class="form-group col-sm-6">
                      <legend>Routes</legend>
                      <div class="form-group row">
                          <label for="startPosition" class="col-sm-4 col-form-label">Start position:</label>
                          <div class="col-sm-6">
                            <input type="text" id="startPosition" name="startPosition" placeholder="Start position" class="form-control"/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="endPosition" class="col-sm-4 col-form-label">End position:</label>
                          <div class="col-sm-6">
                            <input type="text" id="endPosition" name="endPosition" placeholder="End position" class="form-control"/>
                          </div>
                      </div>
                      <div class="form-group row">
                          <label for="duration" class="col-sm-4 col-form-label">Duration:</label>
                          <div class="col-sm-6">
                            <input type="text" id="duration" name="duration" placeholder="Duration" class="form-control"/>
                          </div>
                      </div>
                      <input type="button" id="btnAddRoute" value="Save route" class="btn btn-info"/>
                      <div id="dynamicTable" style="margin-top: 20px"></div>
                  </fieldset>
                  <input type="button" id="btnAddEvent" value="Save event" class="btn btn-primary"/>
              </form>
          </div>
          <div class="alert alert-danger alert1" role="alert">
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
        </div>
      </div>
      <div id="ongoing" class="tab-pane fade">
          <div class="card-columns" id="columns1"></div>
      </div>
      <div id="members" class="tab-pane fade">
          <div class="card-columns" id="columns2"></div>
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
<%--<div class="modal fade" id="redflagModalCenter" tabindex="-1" role="dialog" aria-labelledby="redflagModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="redflagModalLongTitle">Emerency info</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="text" name="redFlagInput" id="redFlagInput"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="btnRedFlag">Submit</button>
            </div>
        </div>
    </div>
</div>--%>
</body>
</html>
